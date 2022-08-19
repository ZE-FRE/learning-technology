package cn.zefre.rabbitmq.service.impl;

import cn.zefre.rabbitmq.entity.MqSendTask;
import cn.zefre.rabbitmq.mapper.MqSendTaskMapper;
import cn.zefre.rabbitmq.service.MqSendTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service("mqSendTaskService")
public class MqSendTaskServiceImpl extends ServiceImpl<MqSendTaskMapper, MqSendTask> implements MqSendTaskService {

    @Resource
    private ObjectMapper objectMapper;

    @Lazy
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public MqSendTask build(String exchange, String routingKey, String queue, Object obj) {
        String message;
        try {
            message = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("序列化异常");
        }
        MqSendTask mqTask = new MqSendTask();
        mqTask.setId("id")
                .setExchangeName(exchange)
                .setRoutingKey(routingKey)
                .setQueueName(queue)
                .setMqMessage(message)
                .setCreateTime(LocalDateTime.now());
        return mqTask;
    }

    @Override
    public void send(MqSendTask mqSendTask) {
        CorrelationData correlationData = new CorrelationData(mqSendTask.getId());
        rabbitTemplate.convertAndSend(mqSendTask.getExchangeName(), mqSendTask.getRoutingKey(), mqSendTask.getMqMessage(), correlationData);
    }

    @Override
    public void updateTaskStatus(String id, Integer status, Boolean updateNumFlag) {
        baseMapper.updateTaskStatus(id, status, updateNumFlag);
    }

}
