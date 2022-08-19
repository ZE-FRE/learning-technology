package cn.zefre.rabbitmq.mapper;

import cn.zefre.rabbitmq.entity.MqSendTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface MqSendTaskMapper extends BaseMapper<MqSendTask> {

    void updateTaskStatus(@Param("id") String id, @Param("status") Integer status, @Param("updateNumFlag") Boolean updateNumFlag);
}
