package cn.zefre.rabbitmq.schedule;

import cn.zefre.rabbitmq.constants.MqSendTaskStatusEnum;
import cn.zefre.rabbitmq.entity.MqSendTask;
import cn.zefre.rabbitmq.service.MqSendTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class MqSendTaskSchedule {

    @Resource
    private MqSendTaskService mqSendTaskService;

    /**
     * MQ消息发送定时任务
     *
     * @author pujian
     * @date 2022/9/21 14:23
     */
    //@Scheduled(cron = "0 */5 * * * ?")
    public void jobMq() {
        // 查询新建状态、发送失败状态的mq消息进行重发
        List<MqSendTask> mqSendTasks = mqSendTaskService.lambdaQuery()
                .in(MqSendTask::getStatus, MqSendTaskStatusEnum.NEW, MqSendTaskStatusEnum.SENT_FAILED)
                .list();
        if (!mqSendTasks.isEmpty()) {
            mqSendTasks.forEach(task -> mqSendTaskService.send(task));
        }
    }

    /**
     * MQ消息删除定时任务
     *
     * @author pujian
     * @date 2022/9/21 14:24
     */
    //@Scheduled(cron = "0 0 0 * * ?")
    public void deleteMqJob() {
        // 删除发送成功且已超过指定天数的mq消息
        int mqDeletionDays = 10;
        LocalDateTime beforeDay = LocalDateTime.now().minusDays(mqDeletionDays);
        mqSendTaskService.lambdaUpdate()
                .eq(MqSendTask::getStatus, MqSendTaskStatusEnum.SENT_SUCCEEDED)
                .le(MqSendTask::getCreateTime, beforeDay)
                .remove();
    }

}
