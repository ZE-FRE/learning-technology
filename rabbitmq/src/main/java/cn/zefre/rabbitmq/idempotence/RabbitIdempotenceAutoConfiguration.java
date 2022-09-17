package cn.zefre.rabbitmq.idempotence;

import cn.zefre.rabbitmq.idempotence.service.IdempotenceService;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 配置保障rabbitmq消费者幂等性功能
 *
 * @author pujian
 * @date 2022/9/17 17:13
 */
@Configuration
@Import(RabbitIdempotenceAutoConfiguration.RabbitIdempotenceRegistrar.class)
@ConditionalOnProperty(prefix = "spring.rabbitmq.listener", name = "type", havingValue = "simple", matchIfMissing = true)
public class RabbitIdempotenceAutoConfiguration {

    public static final String RABBIT_IDEMPOTENCE_SERVICE_BEAN_NAME = "idempotenceService";

    @Bean
    @SuppressWarnings("all")
    public IdempotenceMethodInterceptor idempotentMethodInterceptor(IdempotenceService idempotenceService) {
        return new IdempotenceMethodInterceptor(idempotenceService);
    }

    public static class RabbitIdempotenceRegistrar implements ImportBeanDefinitionRegistrar {

        private static final String RABBIT_IDEMPOTENCE_POST_PROCESSOR_BEAN_NAME = "rabbitIdempotencePostProcessor";

        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
            if (!registry.containsBeanDefinition(RABBIT_IDEMPOTENCE_SERVICE_BEAN_NAME)) {
                ClassLoader classLoader = RabbitIdempotenceRegistrar.class.getClassLoader();
                List<String> list = SpringFactoriesLoader.loadFactoryNames(IdempotenceService.class, classLoader);
                Assert.notEmpty(list, "未能在spring.factories中找到" + IdempotenceService.class.getName() + "的实现");
                String clazzName = list.get(0);
                Class<?> clazz;
                try {
                    clazz = classLoader.loadClass(clazzName);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("没有找到类：" + clazzName);
                }
                AbstractBeanDefinition serviceBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition(clazz).getBeanDefinition();
                registry.registerBeanDefinition(RABBIT_IDEMPOTENCE_SERVICE_BEAN_NAME, serviceBeanDefinition);
            }

            if (!registry.containsBeanDefinition(RABBIT_IDEMPOTENCE_POST_PROCESSOR_BEAN_NAME)) {
                AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(RabbitIdempotencePostProcessor.class).getBeanDefinition();
                beanDefinition.setSynthetic(true);
                registry.registerBeanDefinition(RABBIT_IDEMPOTENCE_POST_PROCESSOR_BEAN_NAME, beanDefinition);
            }
        }

    }

}
