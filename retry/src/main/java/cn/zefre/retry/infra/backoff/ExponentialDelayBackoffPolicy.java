package cn.zefre.retry.infra.backoff;

import cn.zefre.retry.infra.threadpool.RetryThreadPool;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.BackOffPolicy;

import static org.springframework.retry.backoff.ExponentialBackOffPolicy.*;

/**
 * 按指数增长的Backoff
 *
 * @author pujian
 * @date 2023/2/14 9:51
 */
public class ExponentialDelayBackoffPolicy extends AbstractDelayBackoffPolicy implements BackOffPolicy {

    /**
     * The initial backoff interval.
     */
    private volatile long initialInterval = DEFAULT_INITIAL_INTERVAL;

    /**
     * The value to add to the backoff period for each retry attempt.
     */
    private volatile double multiplier = DEFAULT_MULTIPLIER;

    /**
     * The maximum value of the backoff period in milliseconds.
     */
    private volatile long maxInterval = DEFAULT_MAX_INTERVAL;

    public ExponentialDelayBackoffPolicy() {
    }

    public ExponentialDelayBackoffPolicy(long initialInterval, double multiplier, long maxInterval) {
        this.initialInterval = initialInterval;
        this.multiplier = multiplier;
        this.maxInterval = maxInterval;
    }

    public ExponentialDelayBackoffPolicy(RetryThreadPool retryThreadPool, long initialInterval, double multiplier, long maxInterval) {
        super(retryThreadPool);
        this.initialInterval = initialInterval;
        this.multiplier = multiplier;
        this.maxInterval = maxInterval;
    }

    @Override
    public DelayBackoffContext doStart(RetryContext context) {
        return new DelayExponentialBackOffContext(this.initialInterval, this.multiplier, this.maxInterval);
    }

    /**
     * @author pujian
     * @date 2023/2/14 9:49
     */
    public static class DelayExponentialBackOffContext extends AbstractDelayBackoffContext {

        private long interval;

        private final double multiplier;

        private long maxInterval;

        public DelayExponentialBackOffContext(long interval, double multiplier, long maxInterval) {
            this.interval = interval;
            this.multiplier = multiplier;
            this.maxInterval = maxInterval;
        }

        @Override
        public void increaseDelayTime() {
            long delay = (long) (this.interval * this.multiplier);
            if (delay > this.maxInterval) {
                delay = this.maxInterval;
            }
            this.interval = delay;
        }

        @Override
        public long getDelayTime() {
            return this.interval;
        }
    }

}
