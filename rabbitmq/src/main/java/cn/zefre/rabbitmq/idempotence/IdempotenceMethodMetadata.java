package cn.zefre.rabbitmq.idempotence;

import cn.zefre.rabbitmq.idempotence.annotation.RabbitIdempotence;
import org.springframework.amqp.core.AcknowledgeMode;

import java.lang.reflect.Method;

/**
 * rabbitmq幂等方法元数据
 *
 * @author pujian
 * @date 2022/9/16 21:54
 */
public class IdempotenceMethodMetadata {
    /**
     * rabbitmq listener方法
     */
    final Method method;
    /**
     * listener方法的ack模式
     */
    final AcknowledgeMode ackMode;

    final RabbitIdempotence rabbitIdempotence;

    public IdempotenceMethodMetadata(Method method, AcknowledgeMode ackMode, RabbitIdempotence rabbitIdempotence) {
        this.method = method;
        this.ackMode = ackMode;
        this.rabbitIdempotence = rabbitIdempotence;
    }
}
