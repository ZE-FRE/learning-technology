package cn.zefre.retry.infra.threadpool;

import cn.zefre.retry.infra.RetryableTask;
import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author pujian
 * @date 2023/2/14 9:52
 */
@Slf4j
public class RetryScheduleThreadPoolExecutor extends ScheduledThreadPoolExecutor implements RetryThreadPool {

    public RetryScheduleThreadPoolExecutor() {
        super(DEFAULT_CORE_POOL_SIZE, new RetryThreadFactory(), new CallerRunsPolicy());
    }

    @Override
    public void submitRetryableTask(RetryableTask<?, ?> retryableTask) {
        execute(retryableTask);
    }

    @Override
    public void retry(RetryableTask<?, ?> retryableTask, long delayTime, TimeUnit timeUnit) {
        log.debug("delayTime = {}", delayTime);
        schedule(retryableTask, delayTime, timeUnit);
    }

    static class RetryThreadFactory implements ThreadFactory {
        private static final String namePrefix = "retryThread-";
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        @Override
        public Thread newThread(@NotNull Runnable runnable) {
            return new Thread(runnable, namePrefix + threadNumber.getAndIncrement());
        }
    }

}
