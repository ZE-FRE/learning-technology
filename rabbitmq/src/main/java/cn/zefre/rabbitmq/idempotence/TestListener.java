package cn.zefre.rabbitmq.idempotence;

import cn.zefre.rabbitmq.idempotence.annotation.IdempotentRabbitListener;
import com.rabbitmq.client.Channel;
import org.hibernate.validator.constraints.Length;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author pujian
 * @date 2022/9/15 14:40
 */
@Component
@Validated
public class TestListener {

    /*
     * 等价于：
     * @RabbitIdempotence
     * @RabbitListener(queues = "test.queue", ackMode = "MANUAL")
     */
    @IdempotentRabbitListener(queues = "test.queue", ackMode = "MANUAL")
    public void listen(Message message, Channel channel, @Length(min = 2, max = 120) String data) throws IOException {
        System.out.print("监听到消息，内容为：");
        System.out.println("message body = " + new String(message.getBody(), StandardCharsets.UTF_8));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    public void test() {

    }

}
