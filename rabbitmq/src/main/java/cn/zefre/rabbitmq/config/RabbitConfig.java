package cn.zefre.rabbitmq.config;

import cn.zefre.rabbitmq.entity.MqSendTask;
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
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author pujian
 * @date 2022/8/12 16:59
 */
@Configuration
@Slf4j
public class RabbitConfig implements RabbitListenerConfigurer {

    @Resource
    private ConnectionFactory connectionFactory;

    @Resource
    private MqSendTaskService mqSendTaskService;

    /**
     * 死信队列
     */
    public static final String DEAD_LETTER_QUEUE = "deadLetterQueue";
    public static final String DEAD_LETTER_EXCHANGE = "deadLetterExchange";

    /**
     * 死信队列 交换机标识符
     */
    private static final String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
    /**
     * 死信队列交换机绑定键标识符
     */
    private static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";
    /**
     * 死信队列路由
     */
    public static final String DEAD_LETTER_ROUTING = "deadLetterRouting";

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }


    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (correlationData != null) {
                String localMessageId = correlationData.getId();
                if (ack) {
                    log.info("消息id为:{} 的消息，已经发送到交换机", localMessageId);
                    mqSendTaskService.updateTaskStatus(localMessageId, MqSendTask.STATUS_SENT, true);
                } else {
                    log.error("消息id为:{} 的消息，发送到交换机失败，失败原因是：{}", localMessageId, cause);
                    mqSendTaskService.updateTaskStatus(localMessageId, MqSendTask.STATUS_SENT_FAIL, false);
                }
            }
        });
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.error("消息丢失,消息主体message:{},消息主体 message:{},描述:{},消息使用的交换器 exchange:{},消息使用的路由键 routing:{}", message, replyCode, replyText, exchange, routingKey);
            mqSendTaskService.updateTaskStatus(message.getMessageProperties().getCorrelationId(), MqSendTask.STATUS_SENT_FAIL, false);
        });

        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());
        return factory;
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
        admin.declareQueue(new Queue(DEAD_LETTER_QUEUE, true, false, false));
        admin.declareExchange(new DirectExchange(DEAD_LETTER_EXCHANGE, true, false));
        admin.declareBinding(new Binding(DEAD_LETTER_QUEUE, Binding.DestinationType.QUEUE,
                DEAD_LETTER_EXCHANGE, DEAD_LETTER_ROUTING, null));

    }

}
