package cn.zefre.rabbitmq.producer;

import cn.zefre.rabbitmq.entity.MqSendTask;
import cn.zefre.rabbitmq.service.MqSendTaskService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pujian
 * @date 2022/8/29 10:15
 */
@SpringBootTest
public class TestProducer {

    @Resource
    private MqSendTaskService mqSendTaskService;

    @Test
    public void produce() {
        String exchange = "test.exchange";
        String routingKey = "test.routing";
        String queue = "test.queue";
        Map<String, Object> map = new HashMap<>();
        map.put("1", "张三");
        MqSendTask sendTask = mqSendTaskService.build(exchange, routingKey, queue, map);
        mqSendTaskService.send(sendTask);
    }

}
