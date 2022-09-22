package cn.zefre.rabbitmq.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * MQ发送任务状态枚举
 *
 * @author pujian
 * @date 2022/8/29 13:42
 */
public enum MqSendTaskStatusEnum {
    /**
     * 新建状态
     */
    NEW(0),
    /**
     * 发送成功状态
     */
    SENT_SUCCEEDED(1),
    /**
     * 发送失败状态
     */
    SENT_FAILED(-1);

    /**
     * 状态
     */
    @EnumValue
    private Integer status;

    MqSendTaskStatusEnum(Integer status) {
        this.status = status;
    }

}
