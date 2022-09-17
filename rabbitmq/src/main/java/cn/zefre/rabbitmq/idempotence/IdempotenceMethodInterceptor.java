package cn.zefre.rabbitmq.idempotence;

import cn.zefre.rabbitmq.idempotence.service.IdempotenceService;
import com.rabbitmq.client.Channel;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.util.Assert;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * rabbitmq幂等方法拦截
 *
 * @author pujian
 * @date 2022/9/16 23:32
 */
public class IdempotenceMethodInterceptor implements MethodInterceptor {

    /**
     * 幂等服务
     */
    private IdempotenceService idempotenceService;

    private Map<Method, IdempotenceMethodMetadata> eligibleMethodCache;

    public IdempotenceMethodInterceptor(IdempotenceService idempotenceService) {
        this.idempotenceService = idempotenceService;
        this.eligibleMethodCache = new ConcurrentHashMap<>();
    }

    public void addEligibleMethod(Method method, IdempotenceMethodMetadata idempotenceMethodMetadata) {
        eligibleMethodCache.putIfAbsent(method, idempotenceMethodMetadata);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method invocationMethod = invocation.getMethod();
        IdempotenceMethodMetadata idempotenceMetadata = eligibleMethodCache.get(invocationMethod);
        Assert.notNull(idempotenceMetadata, "未能获取方法 " + invocationMethod + " 的幂等元数据");
        Object[] arguments = invocation.getArguments();
        Message message = getDesirableArg(arguments, Message.class, invocationMethod + "方法需要保证幂等性，但是缺少" + Message.class + "参数");
        // 重复消费
        if (isConsumed(message)) {
            Channel channel = getDesirableArg(arguments, Channel.class, invocationMethod + "方法需要手动ack，但是缺少" + Channel.class + "参数");
            basicAckIfNecessary(message, channel, idempotenceMetadata);
            return null;
        }

        Object result = invocation.proceed();

        // 消费完后，需要记录已消费的消息id
        saveAfterConsumption(message);
        return result;
    }

    /**
     * 消息是否已消费
     *
     * @param message 消息对象
     * @return 已消费返回true，未消费返回false
     * @author pujian
     * @date 2022/9/15 22:06
     */
    private boolean isConsumed(Message message) {
        String messageId = getMessageId(message);
        return idempotenceService.exists(messageId);
    }

    private void saveAfterConsumption(Message message) {
        String messageId = getMessageId(message);
        idempotenceService.persist(messageId);
    }

    private void basicAckIfNecessary(Message message, Channel channel, IdempotenceMethodMetadata idempotenceMetadata) throws IOException {
        AcknowledgeMode acknowledgeMode = idempotenceMetadata.ackMode;
        if (AcknowledgeMode.MANUAL == acknowledgeMode) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    private String getMessageId(Message message) {
        String messageId = message.getMessageProperties().getMessageId();
        Assert.hasText(messageId, "需要保证幂等性，但是缺少messageId");
        return messageId;
    }

    @SuppressWarnings("unchecked")
    private <T> T getDesirableArg(Object[] args, Class<T> desirableClass, String errorMsgWhenMismatching) {
        for (Object arg : args) {
            if (desirableClass.isAssignableFrom(arg.getClass())) {
                return (T) arg;
            }
        }
        throw new IllegalArgumentException(errorMsgWhenMismatching);
    }

}
