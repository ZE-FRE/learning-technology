package cn.zefre.rabbitmq.idempotence;

import cn.zefre.rabbitmq.idempotence.annotation.RabbitIdempotence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.BeanExpressionResolver;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * 保障rabbitmq幂等性的后置处理器
 * 对{@link RabbitIdempotence}和{@link RabbitListener}注解共同标注的方法进行幂等性处理
 *
 * @author pujian
 * @date 2022/9/15 20:18
 */
@Slf4j
public class RabbitIdempotencePostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor implements InitializingBean {

    private ConfigurableListableBeanFactory beanFactory;

    private BeanExpressionResolver resolver = new StandardBeanExpressionResolver();

    private BeanExpressionContext expressionContext;

    /**
     * spring.rabbitmq.listener.simple.acknowledge-mode的值
     */
    private final AcknowledgeMode simpleAckMode;
    /**
     * 幂等性处理拦截器
     */
    private IdempotenceMethodInterceptor methodInterceptor;

    public RabbitIdempotencePostProcessor(RabbitProperties rabbitProperties, IdempotenceMethodInterceptor methodInterceptor) {
        log.info("实例化rabbitmq幂等性后置处理器：{}", RabbitIdempotencePostProcessor.class.getName());
        this.simpleAckMode = rabbitProperties.getListener().getSimple().getAcknowledgeMode();
        this.methodInterceptor = methodInterceptor;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        super.setBeanFactory(beanFactory);
        if (beanFactory instanceof ConfigurableListableBeanFactory) {
            this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
            this.resolver = this.beanFactory.getBeanExpressionResolver();
            this.expressionContext = new BeanExpressionContext(this.beanFactory, null);
        }
    }

    @Override
    public void afterPropertiesSet() {
        IdempotentPointcut pointcut = new IdempotentPointcut();
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, this.methodInterceptor);
        advisor.setOrder(Ordered.LOWEST_PRECEDENCE);
        this.advisor = advisor;
    }

    @Override
    protected boolean isEligible(Class<?> targetClass) {
        boolean eligible = super.isEligible(targetClass);
        if (eligible) {
            ReflectionUtils.doWithMethods(targetClass, method -> {
                // 获取方法上所有关联的注解
                MergedAnnotations annotations = MergedAnnotations.from(method, MergedAnnotations.SearchStrategy.TYPE_HIERARCHY);
                if (annotations.isPresent(RabbitIdempotence.class) && annotations.isPresent(RabbitListener.class)) {
                    log.debug("扫描到rabbitmq幂等性方法：" + method);
                    // 看方法上是否有标记手动ACK
                    boolean manualAck = annotations.stream(RabbitListener.class)
                            .map(MergedAnnotation::synthesize)
                            .map(RabbitListener::ackMode)
                            .map(this::resolveAckMode)
                            .anyMatch(AcknowledgeMode.MANUAL::equals);
                    // 得到该方法的ack模式
                    AcknowledgeMode ackMode = manualAck ? AcknowledgeMode.MANUAL : this.simpleAckMode;
                    RabbitIdempotence rabbitIdempotence = annotations.stream(RabbitIdempotence.class).map(MergedAnnotation::synthesize).findFirst().orElse(null);
                    methodInterceptor.addEligibleMethod(method, new IdempotenceMethodMetadata(method, ackMode, rabbitIdempotence));
                }
            }, ReflectionUtils.USER_DECLARED_METHODS);
        }
        return eligible;
    }

    private String resolve(String value) {
        if (this.beanFactory != null) {
            return this.beanFactory.resolveEmbeddedValue(value);
        }
        return value;
    }

    private AcknowledgeMode resolveAckMode(String ackModeAttr) {
        if (StringUtils.hasText(ackModeAttr)) {
            String resolvedValue = resolve(ackModeAttr);
            Object ackMode = this.resolver.evaluate(resolvedValue, this.expressionContext);
            if (ackMode instanceof String) {
                return AcknowledgeMode.valueOf((String) ackMode);
            } else if (ackMode instanceof AcknowledgeMode) {
                return (AcknowledgeMode) ackMode;
            } else {
                Assert.isNull(ackMode, "ackMode must resolve to a String or AcknowledgeMode");
            }
        }
        return null;
    }


    static final class IdempotentPointcut extends StaticMethodMatcherPointcut {
        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            MergedAnnotations annotations = MergedAnnotations.from(method, MergedAnnotations.SearchStrategy.TYPE_HIERARCHY);
            return annotations.isPresent(RabbitIdempotence.class) && annotations.isPresent(RabbitListener.class);
        }
    }

}
