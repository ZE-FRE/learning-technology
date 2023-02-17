package cn.zefre.retry.infra;

import cn.zefre.retry.infra.backoff.AbstractDelayBackoffPolicy;
import cn.zefre.retry.infra.exception.RetryableTaskExecutionException;
import cn.zefre.retry.infra.threadpool.RetryThreadPool;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.RetryState;
import org.springframework.retry.support.DefaultRetryState;
import org.springframework.retry.support.RetryTemplate;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author pujian
 * @date 2023/2/15 11:50
 */
public class RetryExecutor {

    private RetryTemplate retryTemplate;

    private AtomicLong sequencer = new AtomicLong();

    private AbstractDelayBackoffPolicy backoffPolicy;

    private RetryThreadPool retryThreadPool;

    public RetryExecutor(RetryPolicy retryPolicy, AbstractDelayBackoffPolicy backOffPolicy) {
        this.backoffPolicy = backOffPolicy;
        this.retryThreadPool = backoffPolicy.getRetryThreadPool();
        this.retryTemplate = RetryTemplate
                .builder()
                .customPolicy(retryPolicy)
                .customBackoff(backOffPolicy)
                .retryOn(RetryableTaskExecutionException.class)
                .build();
    }


    /**
     * 提交任务
     *
     * @param retryCallback 任务
     * @author pujian
     * @date 2023/2/15 16:31
     */
    public <T, E extends Throwable> void submit(RetryCallback<T, E> retryCallback) {
        long sequenceNum = sequencer.getAndIncrement();
        RetryState retryState = new DefaultRetryState("retryableTask" + sequenceNum);
        RetryableTask<T, E> retryableTask = new RetryableTask<>(retryTemplate, retryCallback, retryState);
        this.backoffPolicy.cacheTask(retryState.getKey(), retryableTask);

        retryThreadPool.submitRetryableTask(retryableTask);
    }


}
