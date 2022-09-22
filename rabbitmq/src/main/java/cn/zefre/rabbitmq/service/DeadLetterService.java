package cn.zefre.rabbitmq.service;

import cn.zefre.rabbitmq.entity.DeadLetter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 死信消息Service
 *
 * @author pujian
 * @date 2022/8/30 10:09
 */
public interface DeadLetterService extends IService<DeadLetter> {

    void pushMessage(List<String> idList);

}
