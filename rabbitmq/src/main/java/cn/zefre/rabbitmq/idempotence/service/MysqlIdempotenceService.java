package cn.zefre.rabbitmq.idempotence.service;

import cn.zefre.rabbitmq.entity.MqIdempotenceMessage;
import cn.zefre.rabbitmq.mapper.MqIdempotenceMessageMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 基于Mysql提供幂等性服务
 *
 * @author pujian
 * @date 2022/9/17 19:03
 */
public class MysqlIdempotenceService extends ServiceImpl<MqIdempotenceMessageMapper, MqIdempotenceMessage> implements IdempotenceService {

    @Override
    public boolean exists(String uniqueId) {
        return lambdaQuery().eq(MqIdempotenceMessage::getMessageId, uniqueId).exists();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void persist(String uniqueId) {
        MqIdempotenceMessage mqIdempotenceMessage = new MqIdempotenceMessage().setMessageId(uniqueId).setCreateTime(LocalDateTime.now());
        save(mqIdempotenceMessage);
    }



    /**
     * 定时任务：每个月1号凌晨1点执行
     * 删除上个月之前的数据，最多保留两个月的数据(上个月和本月)
     *
     * @author pujian
     * @date 2022/9/19 13:40
     */
    //@Scheduled(cron = "0 0 1 1 * ?")
    @Transactional(rollbackFor = Exception.class)
    public void deleteRedundantData() {
        // 取得前一个月的时间
        LocalDateTime priorMonth = LocalDateTime.now().minusMonths(1);
        lambdaUpdate().lt(MqIdempotenceMessage::getCreateTime, priorMonth).remove();
    }

}
