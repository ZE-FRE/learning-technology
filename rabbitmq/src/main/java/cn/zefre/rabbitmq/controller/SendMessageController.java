package cn.zefre.rabbitmq.controller;

import cn.zefre.base.web.annotation.WrapResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author pujian
 * @date 2022/8/12 16:36
 */
@WrapResponse
@RestController
public class SendMessageController {

    @Lazy
    @Resource
    private RabbitTemplate rabbitTemplate;

    @GetMapping("send")
    public String send() {

        return "发送mq消息成功";
    }

}
