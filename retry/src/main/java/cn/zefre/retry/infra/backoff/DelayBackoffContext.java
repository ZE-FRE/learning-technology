package cn.zefre.retry.infra.backoff;

import cn.zefre.retry.infra.RetryableTask;
import org.springframework.retry.backoff.BackOffContext;

import java.util.concurrent.TimeUnit;

/**
 * @author pujian
 * @date 2023/2/15 10:34
 */
public interface DelayBackoffContext extends BackOffContext {

    /**
     * 增加延迟时间
     *
     * @author pujian
     * @date 2023/2/15 13:49
     */
    void increaseDelayTime();

    /**
     * 获取延迟时间
     *
     * @return long
     * @author pujian
     * @date 2023/2/15 13:50
     */
    long getDelayTime();

    /**
     * 设置延迟时间单位
     *
     * @param timeUnit timeUnit
     * @author pujian
     * @date 2023/2/16 9:27
     */
    void setTimeUnit(TimeUnit timeUnit);

    /**
     * 获取延迟时间单位
     *
     * @return java.util.concurrent.TimeUnit
     * @author pujian
     * @date 2023/2/16 9:27
     */
    TimeUnit getTimeUnit();

    /**
     * 给当前{@link BackOffContext}上下文设置{@link RetryableTask}
     *
     * @param retryableTask retryableTask
     * @author pujian
     * @date 2023/2/15 17:50
     */
    void setRetryableTask(RetryableTask<?, ?> retryableTask);

    /**
     * 获取当前{@link BackOffContext}上下文中的{@link RetryableTask}
     *
     * @return com.ytsw.retry.infra.RetryableTask<?,?>
     * @author pujian
     * @date 2023/2/15 17:51
     */
    RetryableTask<?, ?> getRetryableTask();

}
