package cn.zefre.rabbitmq.controller;

import cn.zefre.base.web.response.UniformResponse;
import cn.zefre.rabbitmq.service.DeadLetterService;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 死信消息Rest
 *
 * @author pujian
 * @date 2022/9/21 10:21
 */
@Validated
@RestController
@RequestMapping("/deadLetter")
@Api(value = "DeadLetterRest", tags = "死信消息")
public class DeadLetterRest {

    @Resource
    private DeadLetterService deadLetterService;

    /**
     * 推送
     *
     * @param idList 死信消息id集合
     * @return cn.rivamed.framework.common.CommonResult<?>
     * @author pujian
     * @date 2022/9/21 16:04
     */
    @PostMapping("pushMessage")
    public UniformResponse pushMessage(@RequestBody @NotEmpty List<@NotEmpty String> idList) {
        deadLetterService.pushMessage(idList);
        return UniformResponse.ok();
    }

}
