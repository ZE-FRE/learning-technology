package cn.zefre.rabbitmq.idempotence.service;

/**
 * 基于Redis提供幂等性服务
 *
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
