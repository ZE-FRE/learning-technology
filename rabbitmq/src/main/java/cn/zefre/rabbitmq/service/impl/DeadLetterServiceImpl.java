package cn.zefre.rabbitmq.service.impl;

import cn.zefre.rabbitmq.entity.DeadLetter;
import cn.zefre.rabbitmq.entity.MqSendTask;
import cn.zefre.rabbitmq.mapper.DeadLetterMapper;
import cn.zefre.rabbitmq.service.DeadLetterService;
import cn.zefre.rabbitmq.service.MqSendTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pujian
 * @date 2022/8/30 10:10
 */
@Service
public class DeadLetterServiceImpl extends ServiceImpl<DeadLetterMapper, DeadLetter> implements DeadLetterService {

    @Resource
    private MqSendTaskService mqSendTaskService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pushMessage(List<String> idList) {
        List<DeadLetter> deadLetters = lambdaQuery()
                .in(DeadLetter::getDeadLetterId, idList)
                .eq(DeadLetter::getStatus, DeadLetter.STATUS_UNHANDLED)
                .list();
        deadLetters.forEach(deadLetter -> deadLetter.setStatus(DeadLetter.STATUS_HANDLED));
        List<MqSendTask> sendTasks = new ArrayList<>(deadLetters.size());
        for (DeadLetter deadLetter : deadLetters) {
            MqSendTask sendTask = mqSendTaskService.build(deadLetter.getExchangeName(), deadLetter.getRoutingKey(),
                    deadLetter.getQueueName(), deadLetter.getMessageBody());
            sendTasks.add(sendTask);
        }
        if (!sendTasks.isEmpty()) {
            updateBatchById(deadLetters);
            mqSendTaskService.saveBatch(sendTasks);
            sendTasks.forEach(task -> mqSendTaskService.send(task));
        }
    }

}
