package cn.zefre.retry.infra.backoff;

import cn.zefre.retry.infra.RetryableTask;

import java.util.concurrent.TimeUnit;

/**
 * @author pujian
 * @date 2023/2/15 17:48
 */
public abstract class AbstractDelayBackoffContext implements DelayBackoffContext {

    public static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MILLISECONDS;

    protected TimeUnit timeUnit = DEFAULT_TIME_UNIT;

    protected RetryableTask<?, ?> retryableTask;

    public AbstractDelayBackoffContext() {
        setTimeUnit(DEFAULT_TIME_UNIT);
    }

    @Override
    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    @Override
    public TimeUnit getTimeUnit() {
        return this.timeUnit;
    }

    @Override
    public void setRetryableTask(RetryableTask<?, ?> retryableTask) {
        this.retryableTask = retryableTask;
    }

    @Override
    public RetryableTask<?, ?> getRetryableTask() {
        return retryableTask;
    }

}
