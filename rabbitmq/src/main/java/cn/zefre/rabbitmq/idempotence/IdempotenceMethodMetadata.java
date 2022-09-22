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
     * listener的ack模式，注意与rabbitmq自身的ack模式的区别
     * NONE：无ack模式，rabbitmq默认推送的所有消息都已经消费成功，会不断地向消费端推送消息。消费端如果消费速度过慢，消费者内存可能会存在大量消息。
     * AUTO：由spring进行手动ack和nack(大概逻辑是：当listener方法正常执行完毕，没有抛异常时，spring会向rabbitmq服务器应答ack，抛出异常时，应答nack)
     * MANUAL：手动ack，由用户通过Channel对象手动ack/nack
     * @see AcknowledgeMode
     */
    final AcknowledgeMode ackMode;

    final RabbitIdempotence rabbitIdempotence;

    public IdempotenceMethodMetadata(Method method, AcknowledgeMode ackMode, RabbitIdempotence rabbitIdempotence) {
        this.method = method;
        this.ackMode = ackMode;
        this.rabbitIdempotence = rabbitIdempotence;
    }
}
