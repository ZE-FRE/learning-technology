package cn.zefre.rabbitmq.idempotence.service;

/**
 * @author pujian
 * @date 2022/9/16 22:12
 */
public class RedisIdempotenceService implements IdempotenceService {

    @Override
    public boolean exists(String uniqueId) {
        System.out.println("验证消息id：" + uniqueId);
        return false;
    }

    @Override
    public void persist(String uniqueId) {
        System.out.println("消息id：" + uniqueId + " 已保存");
    }

}
