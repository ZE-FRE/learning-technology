package cn.zefre.rabbitmq.idempotence.service;

/**
 * 幂等性Service
 *
 * @author pujian
 * @date 2022/9/16 20:50
 */
public interface IdempotenceService {

    /**
     * 验证唯一id是否已存在
     *
     * @param uniqueId 幂等性唯一id
     * @return 唯一id已存在返回true，不存在返回false
     * @author pujian
     * @date 2022/9/16 22:16
     */
    boolean exists(String uniqueId);

    /**
     * 保存唯一id
     *
     * @param uniqueId 幂等性唯一id
     * @author pujian
     * @date 2022/9/17 21:33
     */
    void persist(String uniqueId);

}
