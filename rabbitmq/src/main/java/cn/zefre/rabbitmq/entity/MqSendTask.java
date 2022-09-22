package cn.zefre.rabbitmq.entity;

import cn.zefre.rabbitmq.constants.MqSendTaskStatusEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@TableName("t_mq_send_task")
public class MqSendTask implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * send_task_id
     */
    @TableId(value = "send_task_id", type = IdType.ASSIGN_UUID)
    private String sendTaskId;
    /**
     * exchange_name
     */
    @TableField(value = "exchange_name")
    private String exchangeName;
    /**
     * routing_key
     */
    @TableField(value = "routing_key")
    private String routingKey;
    /**
     * queue_name
     */
    @TableField(value = "queue_name")
    private String queueName;
    /**
     * mq_message
     */
    @TableField(value = "message_body")
    private String messageBody;
    /**
     * create_time
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;
    /**
     * status
     */
    @TableField(value = "status")
    private MqSendTaskStatusEnum status;
    /**
     * sent_num
     */
    @TableField(value = "sent_num")
    private Integer sentNum = 0;

}
