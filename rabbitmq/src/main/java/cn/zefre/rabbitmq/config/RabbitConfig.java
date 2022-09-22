package cn.zefre.rabbitmq.config;

import cn.zefre.rabbitmq.constants.DeadLetterConstant;
import cn.zefre.rabbitmq.constants.MqSendTaskStatusEnum;
import cn.zefre.rabbitmq.service.MqSendTaskService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author pujian
 * @date 2022/8/12 16:59
 */
@Slf4j
@Configuration
public class RabbitConfig {

    @Resource
    private ConnectionFactory connectionFactory;

    @Resource
    private MqSendTaskService mqSendTaskService;


    @Bean
    @Primary
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = getMandatoryRabbitTemplate();
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    /**
     * 注入使用{@link org.springframework.messaging.converter.SimpleMessageConverter}的RabbitTemplate Bean
     *
     * @return org.springframework.amqp.rabbit.core.RabbitTemplate
     * @author pujian
     * @date 2022/8/29 13:34
     */
    @Bean("simpleMessageConverterRabbitTemplate")
    public RabbitTemplate simpleMessageConverterRabbitTemplate() {
        return getMandatoryRabbitTemplate();
    }

    private RabbitTemplate getMandatoryRabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (correlationData != null) {
                String localMessageId = correlationData.getId();
                if (ack) {
                    log.debug("消息id为:{} 的消息，已经发送到交换机", localMessageId);
                    mqSendTaskService.updateTaskStatus(localMessageId, MqSendTaskStatusEnum.SENT_SUCCEEDED);
                } else {
                    log.error("消息id为:{} 的消息，发送到交换机失败，失败原因是：{}", localMessageId, cause);
                    mqSendTaskService.updateTaskStatus(localMessageId, MqSendTaskStatusEnum.SENT_FAILED);
                }
            }
        });
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.error("消息丢失,消息主体message:{},消息主体 message:{},描述:{},消息使用的交换器 exchange:{},消息使用的路由键 routing:{}", message, replyCode, replyText, exchange, routingKey);
            mqSendTaskService.updateTaskStatus(message.getMessageProperties().getCorrelationId(), MqSendTaskStatusEnum.SENT_FAILED);
        });

        return rabbitTemplate;
    }

    @Bean
    public MessageConverter messageConverter() {
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(om);
    }

    @PostConstruct
    public void produceQueue() {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        //创建死信队列
        admin.declareExchange(new DirectExchange(DeadLetterConstant.DEFAULT_DEAD_LETTER_EXCHANGE, true, false));
        admin.declareQueue(new Queue(DeadLetterConstant.DEFAULT_DEAD_LETTER_QUEUE, true, false, false));
        admin.declareBinding(new Binding(DeadLetterConstant.DEFAULT_DEAD_LETTER_QUEUE, Binding.DestinationType.QUEUE,
                DeadLetterConstant.DEFAULT_DEAD_LETTER_EXCHANGE, DeadLetterConstant.DEFAULT_DEAD_LETTER_ROUTING, null));
    }

}
