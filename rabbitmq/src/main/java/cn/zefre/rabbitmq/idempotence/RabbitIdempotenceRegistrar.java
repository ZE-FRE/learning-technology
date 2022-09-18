package cn.zefre.rabbitmq.idempotence;

import cn.zefre.rabbitmq.idempotence.annotation.EnableRabbitIdempotence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

/**
 * 注册rabbitmq消费者幂等性功能所需的bean定义
 *
 * @author pujian
 * @date 2022/9/17 17:13
 */
@Slf4j
public class RabbitIdempotenceRegistrar implements ImportBeanDefinitionRegistrar {

    public static final String RABBIT_IDEMPOTENCE_SERVICE_BEAN_NAME = "idempotenceService";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGenerator) {
        // 从注解上获取rabbitmq幂等性服务提供者
        AnnotationAttributes attributes = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(EnableRabbitIdempotence.class.getName(), false));
        Assert.notNull(attributes, "should never happen");
        Class<?> serviceProvider = attributes.getClass("serviceProvider");

        if (!registry.containsBeanDefinition(RABBIT_IDEMPOTENCE_SERVICE_BEAN_NAME)) {
            log.info("rabbitmq幂等性服务提供者是：{}", serviceProvider.getName());
            BeanDefinition serviceDefinition = BeanDefinitionBuilder.genericBeanDefinition(serviceProvider).getBeanDefinition();
            registry.registerBeanDefinition(RABBIT_IDEMPOTENCE_SERVICE_BEAN_NAME, serviceDefinition);
        }

        BeanDefinition methodInterceptorDefinition = BeanDefinitionBuilder.genericBeanDefinition(IdempotenceMethodInterceptor.class).getBeanDefinition();
        registry.registerBeanDefinition(beanNameGenerator.generateBeanName(methodInterceptorDefinition, registry), methodInterceptorDefinition);

        AbstractBeanDefinition postProcessorDefinition = BeanDefinitionBuilder.genericBeanDefinition(RabbitIdempotencePostProcessor.class).getBeanDefinition();
        postProcessorDefinition.setSynthetic(true);
        registry.registerBeanDefinition(beanNameGenerator.generateBeanName(postProcessorDefinition, registry), postProcessorDefinition);
    }

}
