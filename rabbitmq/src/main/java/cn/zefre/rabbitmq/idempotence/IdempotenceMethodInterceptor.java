package cn.zefre.rabbitmq.idempotence;

import cn.zefre.rabbitmq.idempotence.service.IdempotenceService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.context.annotation.Lazy;
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
@Slf4j
public class IdempotenceMethodInterceptor implements MethodInterceptor {

    /**
     * 幂等服务
     */
    private IdempotenceService idempotenceService;

    /**
     * 幂等性方法元数据缓存
     */
    private Map<Method, IdempotenceMethodMetadata> eligibleMethodCache;

    public IdempotenceMethodInterceptor(@Lazy IdempotenceService idempotenceService) {
        this.idempotenceService = idempotenceService;
        this.eligibleMethodCache = new ConcurrentHashMap<>();
    }

    public void addEligibleMethod(Method method, IdempotenceMethodMetadata idempotenceMethodMetadata) {
        eligibleMethodCache.putIfAbsent(method, idempotenceMethodMetadata);
    }


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object result;
        Method invocationMethod = invocation.getMethod();
        IdempotenceMethodMetadata idempotenceMetadata = eligibleMethodCache.get(invocationMethod);
        Assert.notNull(idempotenceMetadata, "未能获取方法 " + invocationMethod + " 的幂等元数据");
        Object[] arguments = invocation.getArguments();
        Message message = getDesirableArg(arguments, Message.class,
                idempotenceMetadata.method + "方法需要保证幂等性，但是缺少" + Message.class.getName() + "参数");
        // 如果该消息已经被消费，则不再重复消费
        if (isConsumed(message)) {
            // 若消费者是AcknowledgeMode.MANUAL模式，则需要进行手动ack应答
            basicAckIfNecessary(arguments, message, idempotenceMetadata);
            return null;
        }

        result = invocation.proceed();

        // 消费完后，需要记录该消息已消费
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

    private void basicAckIfNecessary(Object[] arguments, Message message, IdempotenceMethodMetadata idempotenceMetadata) throws IOException {
        AcknowledgeMode acknowledgeMode = idempotenceMetadata.ackMode;
        if (AcknowledgeMode.MANUAL == acknowledgeMode) {
            Channel channel = getDesirableArg(arguments, Channel.class,
                    idempotenceMetadata.method + "方法需要手动ack，但是缺少" + Channel.class.getName() + "参数");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    private String getMessageId(Message message) {
        String messageId = message.getMessageProperties().getMessageId();
        Assert.hasText(messageId, "需要保证幂等性，但是缺少messageId");
        return messageId;
    }

    /**
     * 从参数数组中获取想要的参数
     *
     * @param args                    args
     * @param desirableClass          目标参数类型
     * @param errorMsgWhenMismatching 参数不存在时的异常信息
     * @return T
     * @author pujian
     * @date 2022/9/19 17:46
     */
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
