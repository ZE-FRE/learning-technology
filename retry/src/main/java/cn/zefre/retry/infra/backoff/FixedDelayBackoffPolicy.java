package cn.zefre.retry.infra.backoff;

import cn.zefre.retry.infra.threadpool.RetryThreadPool;
import org.springframework.retry.RetryContext;

/**
 * 固定延迟策略
 *
 * @author pujian
 * @date 2023/2/16 10:12
 */
public class FixedDelayBackoffPolicy extends AbstractDelayBackoffPolicy {

    private long delayTime;

    public FixedDelayBackoffPolicy(long delayTime) {
        this.delayTime = delayTime;
    }

    public FixedDelayBackoffPolicy(RetryThreadPool retryThreadPool, long delayTime) {
        super(retryThreadPool);
        this.delayTime = delayTime;
    }

    @Override
    public DelayBackoffContext doStart(RetryContext context) {
        return new FixedDelayBackoffContext(delayTime);
    }

    static class FixedDelayBackoffContext extends AbstractDelayBackoffContext {

        public FixedDelayBackoffContext(long delayTime) {
            this.delayTime = delayTime;
        }

        private long delayTime;

        @Override
        public void increaseDelayTime() {
            // do nothing
        }

        @Override
        public long getDelayTime() {
            return delayTime;
        }

    }

}
