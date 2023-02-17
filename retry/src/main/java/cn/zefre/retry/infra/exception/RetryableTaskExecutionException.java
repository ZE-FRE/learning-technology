package cn.zefre.retry.infra.exception;

/**
 * 可重试任务执行异常
 *
 * @author pujian
 * @date 2023/2/16 10:31
 */
public class RetryableTaskExecutionException extends RuntimeException {

    public RetryableTaskExecutionException(String message) {
        super(message);
    }

    public RetryableTaskExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
