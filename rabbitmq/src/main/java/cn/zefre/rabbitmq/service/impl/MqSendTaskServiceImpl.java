package cn.zefre.rabbitmq.service.impl;

import cn.zefre.base.util.UuidUtil;
import cn.zefre.rabbitmq.constants.MqSendTaskStatusEnum;
import cn.zefre.rabbitmq.entity.MqSendTask;
import cn.zefre.rabbitmq.mapper.MqSendTaskMapper;
import cn.zefre.rabbitmq.service.MqSendTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @SneakyThrows
    public MqSendTask build(String exchange, String routingKey, String queue, Object obj) {
        MqSendTask mqTask = new MqSendTask();
        mqTask.setSendTaskId(UuidUtil.generatorUuid())
                .setExchangeName(exchange)
                .setRoutingKey(routingKey)
                .setQueueName(queue)
                .setMessageBody(objectMapper.writeValueAsString(obj))
                .setStatus(MqSendTaskStatusEnum.NEW)
                .setCreateTime(LocalDateTime.now());
        return mqTask;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(MqSendTask mqSendTask) {
        CorrelationData correlationData = new CorrelationData(mqSendTask.getSendTaskId());
        MessageProperties messageProperties = new MessageProperties();
        // 设置messageId，给消费端做幂等性
        messageProperties.setMessageId(mqSendTask.getSendTaskId());
        // 生成消息对象
        MessageConverter messageConverter = rabbitTemplate.getMessageConverter();
        Message message = messageConverter.toMessage(mqSendTask.getMessageBody(), messageProperties);
        // 发送消息
        rabbitTemplate.send(mqSendTask.getExchangeName(), mqSendTask.getRoutingKey(), message, correlationData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTaskStatus(String id, MqSendTaskStatusEnum status) {
        baseMapper.updateTaskStatus(id, status);
    }

}
