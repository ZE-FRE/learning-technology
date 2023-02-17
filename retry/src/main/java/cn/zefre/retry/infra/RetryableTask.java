package cn.zefre.retry.infra;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryState;
import org.springframework.retry.support.RetryTemplate;

/**
 * @author pujian
 * @date 2023/2/14 11:27
 */
@Data
@Slf4j
public class RetryableTask<T, E extends Throwable> implements Runnable {

    private RetryTemplate retryTemplate;

    private RetryCallback<T, E> retryCallback;

    private RetryState retryState;

    public RetryableTask(RetryTemplate retryTemplate,
                         RetryCallback<T, E> retryCallback,
                         RetryState retryState) {
        this.retryTemplate = retryTemplate;
        this.retryCallback = retryCallback;
        this.retryState = retryState;
    }

    @Override
    public void run() {
        try {
            retryTemplate.execute(retryCallback, retryState);
        } catch (Throwable e) {
            log.error(e.getMessage());
        }
    }

}
