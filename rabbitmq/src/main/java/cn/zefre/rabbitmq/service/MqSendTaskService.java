package cn.zefre.rabbitmq.service;

import cn.zefre.rabbitmq.constants.MqSendTaskStatusEnum;
import cn.zefre.rabbitmq.entity.MqSendTask;
import com.baomidou.mybatisplus.extension.service.IService;

public interface MqSendTaskService extends IService<MqSendTask> {

    MqSendTask build(String exchange, String routingKey, String queue, Object obj);

    void send(MqSendTask mqSendTask);

    void updateTaskStatus(String id, MqSendTaskStatusEnum status);

}
