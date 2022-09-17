package cn.zefre.rabbitmq.idempotence.annotation;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 保证幂等性的{@link RabbitListener}
 *
 * @author pujian
 * @date 2022/9/15 14:43
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RabbitListener
@RabbitIdempotence
public @interface IdempotentRabbitListener {

    /**
     * 关联{@link RabbitListener#queues()}
     */
    @AliasFor(annotation = RabbitListener.class, value = "queues")
    String[] queues() default {};

    /**
     * 关联{@link RabbitListener#ackMode()}
     */
    @AliasFor(annotation = RabbitListener.class, value = "ackMode")
    String ackMode() default "";

    /**
     * 关联{@link RabbitListener#concurrency()}
     */
    @AliasFor(annotation = RabbitListener.class, value = "concurrency")
    String concurrency() default "";

}
