package cn.zefre.retry.infra;

import cn.zefre.retry.infra.backoff.ExponentialDelayBackoffPolicy;
import cn.zefre.retry.infra.exception.RetryableTaskExecutionException;
import org.junit.jupiter.api.Test;
import org.springframework.retry.policy.MaxAttemptsRetryPolicy;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author pujian
 * @date 2023/2/13 16:01
 */
public class RetryExecutorTest {

    @Test
    public void testSubmit() throws InterruptedException {
        RetryExecutor retryExecutor = new RetryExecutor(new MaxAttemptsRetryPolicy(5),
                new ExponentialDelayBackoffPolicy(1000L, 2, 30000L));

        retryExecutor.submit(retryContext -> {
            System.out.println("do business，时间：" + LocalDateTime.now());
            throw new RetryableTaskExecutionException("出现异常");
        });
        TimeUnit.SECONDS.sleep(20);

    }

}
