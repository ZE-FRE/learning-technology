package cn.zefre.retry.infra.backoff;

import cn.zefre.retry.infra.RetryableTask;
import cn.zefre.retry.infra.threadpool.RetryScheduleThreadPoolExecutor;
import cn.zefre.retry.infra.threadpool.RetryThreadPool;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.BackOffContext;
import org.springframework.retry.backoff.BackOffInterruptedException;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author pujian
 * @date 2023/2/15 13:40
 */
public abstract class AbstractDelayBackoffPolicy implements BackOffPolicy {

    private Map<Object, RetryableTask<?, ?>> retryableTaskCache = new ConcurrentHashMap<>();

    private RetryThreadPool retryThreadPool;

    public AbstractDelayBackoffPolicy() {
        this.retryThreadPool = new RetryScheduleThreadPoolExecutor();
    }

    public AbstractDelayBackoffPolicy(RetryThreadPool retryThreadPool) {
        this.retryThreadPool = retryThreadPool;
    }

    @Override
    public BackOffContext start(RetryContext context) {
        Object retryStateKey = context.getAttribute(RetryContext.STATE_KEY);
        Assert.notNull(retryStateKey, "retryStateKey cannot be null.");
        RetryableTask<?, ?> retryableTask = retryableTaskCache.get(retryStateKey);
        retryableTaskCache.remove(retryStateKey);
        Assert.notNull(retryableTask, "retryStateKey cannot be null.");
        DelayBackoffContext delayBackoffContext = doStart(context);
        delayBackoffContext.setRetryableTask(retryableTask);
        return delayBackoffContext;
    }

    @Override
    public void backOff(BackOffContext backOffContext) throws BackOffInterruptedException {
        DelayBackoffContext context = (DelayBackoffContext) backOffContext;
        long delayTime = context.getDelayTime();
        context.increaseDelayTime();
        retryThreadPool.retry(context.getRetryableTask(), delayTime, context.getTimeUnit());
    }

    /**
     * 实例化{@link DelayBackoffContext}
     *
     * @param context {@link RetryContext}
     * @return org.springframework.retry.backoff.BackOffContext
     * @author pujian
     * @date 2023/2/15 13:50
     */
    public abstract DelayBackoffContext doStart(RetryContext context);


    /**
     * 缓存{@link RetryableTask}
     *
     * @param retryStateKey retryStateKey
     * @param retryableTask retryableTask
     * @author pujian
     * @date 2023/2/15 16:27
     */
    public void cacheTask(Object retryStateKey, RetryableTask<?, ?> retryableTask) {
        retryableTaskCache.put(retryStateKey, retryableTask);
    }

    public RetryThreadPool getRetryThreadPool() {
        return retryThreadPool;
    }

}
