package cn.zefre.retry.infra.backoff;

import cn.zefre.retry.infra.exception.RetryTerminatedException;
import cn.zefre.retry.infra.threadpool.RetryThreadPool;
import org.springframework.retry.RetryContext;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author pujian
 * @date 2023/2/15 14:04
 */
public class GapDelayBackoffPolicy extends AbstractDelayBackoffPolicy {

    public GapDelayBackoffPolicy(List<Long> delayTimeList) {
        Assert.notEmpty(delayTimeList, "retryTimeList cannot be empty.");
        this.delayTimeList = delayTimeList;
    }

    public GapDelayBackoffPolicy(RetryThreadPool retryThreadPool, List<Long> delayTimeList) {
        super(retryThreadPool);
        Assert.notEmpty(delayTimeList, "retryTimeList cannot be empty.");
        this.delayTimeList = delayTimeList;
    }

    private List<Long> delayTimeList;

    @Override
    public DelayBackoffContext doStart(RetryContext context) {
        return new GapDelayBackoffContext(delayTimeList.toArray(new Long[0]));
    }

    static class GapDelayBackoffContext extends AbstractDelayBackoffContext {

        private Long[] delayTimes;

        private int index = 0;

        public GapDelayBackoffContext(Long[] delayTimes) {
            this.delayTimes = delayTimes;
        }

        @Override
        public void increaseDelayTime() {
            if (index >= delayTimes.length) {
                throw new RetryTerminatedException("已达到最大重试次数");
            }
            index++;
        }

        @Override
        public long getDelayTime() {
            return delayTimes[index];
        }
    }

}
