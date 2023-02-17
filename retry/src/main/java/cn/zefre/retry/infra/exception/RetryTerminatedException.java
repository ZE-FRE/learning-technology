package cn.zefre.retry.infra.exception;

/**
 * @author pujian
 * @date 2023/2/16 10:19
 */
public class RetryTerminatedException extends RuntimeException {

    public RetryTerminatedException(String message) {
        super(message);
    }

}
