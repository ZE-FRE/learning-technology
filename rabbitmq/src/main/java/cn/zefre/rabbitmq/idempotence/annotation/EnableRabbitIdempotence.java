package cn.zefre.rabbitmq.idempotence.annotation;

import cn.zefre.rabbitmq.idempotence.RabbitIdempotenceRegistrar;
import cn.zefre.rabbitmq.idempotence.service.IdempotenceService;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启rabbitmq消费者幂等性自动验证
 *
 * @author pujian
 * @date 2022/9/17 12:37
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(RabbitIdempotenceRegistrar.class)
public @interface EnableRabbitIdempotence {

    /**
     * 幂等性服务提供者
     */
    Class<? extends IdempotenceService> serviceProvider();

}
