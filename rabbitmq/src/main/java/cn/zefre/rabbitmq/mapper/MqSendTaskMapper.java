package cn.zefre.rabbitmq.mapper;

import cn.zefre.rabbitmq.constants.MqSendTaskStatusEnum;
import cn.zefre.rabbitmq.entity.MqSendTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface MqSendTaskMapper extends BaseMapper<MqSendTask> {

    @Update("UPDATE t_trans_mq_send_task SET status = #{status},sent_num = sent_num + 1 WHERE id = #{id}")
    void updateTaskStatus(@Param("id") String id, @Param("status") MqSendTaskStatusEnum status);

}
