package cn.zefre.rabbitmq.idempotence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * rabbitmq消费者幂等性
 * 在处理ack模式是AcknowledgeMode.MANUAL的方法时，如果是重复消费，则会帮助完成手动ack
 *
 * @author pujian
 * @date 2022/9/15 14:31
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RabbitIdempotence {

}
