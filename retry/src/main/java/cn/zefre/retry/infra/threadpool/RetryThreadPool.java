package cn.zefre.retry.infra.threadpool;

import cn.zefre.retry.infra.RetryableTask;

import java.util.concurrent.TimeUnit;

/**
 * 用于执行可重试任务
 *
 * @author pujian
 * @date 2023/2/15 16:02
 */
public interface RetryThreadPool {

    int DEFAULT_CORE_POOL_SIZE = 20;

    int DEFAULT_MAXIMUM_POOL_SIZE = 100;

    long DEFAULT_KEEP_ALIVE_TIME = 3000;

    TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MILLISECONDS;

    /**
     * 提交任务
     *
     * @param retryableTask 任务
     * @author pujian
     * @date 2023/2/15 16:10
     */
    void submitRetryableTask(RetryableTask<?, ?> retryableTask);

    /**
     * 任务重试
     *
     * @param retryableTask 任务
     * @param delayTime 延迟时间
     * @param timeUnit 时间单位
     * @author pujian
     * @date 2023/2/15 16:10
     */
    void retry(RetryableTask<?, ?> retryableTask, long delayTime, TimeUnit timeUnit);

}
