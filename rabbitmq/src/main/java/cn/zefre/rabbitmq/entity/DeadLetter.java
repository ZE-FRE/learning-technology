package cn.zefre.rabbitmq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 死信消息
 *
 * @author pujian
 * @date 2022/8/30 10:06
 */

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@TableName("t_dead_letter")
public class DeadLetter {

    /**
     * 未处理
     */
    public static final  String STATUS_UNHANDLED = "0";
    /**
     * 已处理
     */
    public static final  String STATUS_HANDLED = "1";

    /**
     * id
     */
    @TableId(value = "dead_letter_id", type = IdType.ASSIGN_UUID)
    private String deadLetterId;
    /**
     * 交换机名称
     */
    @TableField("exchange_name")
    private String exchangeName;
    /**
     * 路由键
     */
    @TableField("routing_key")
    private String routingKey;

    /**
     * 队列名称
     */
    @TableField("queue_name")
    private String queueName;

    /**
     * 消息体
     */
    @TableField(value="message_body")
    private String messageBody;

    /**
     * 是否处理 1：已处理；0：未处理
     */
    @TableField("status")
    private String status;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

}
