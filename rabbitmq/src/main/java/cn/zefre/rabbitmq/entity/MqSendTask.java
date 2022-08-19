package cn.zefre.rabbitmq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@TableName("t_trans_mq_send_task")
public class MqSendTask implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Integer STATUS_INIT = 0;

    public static final Integer STATUS_SENT = 1;

    public static final Integer STATUS_SENT_FAIL = -1;
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * mq_message
     */
    @TableField(value="mq_message")
    private String mqMessage;
    /**
     * exchange_name
     */
    @TableField(value="exchange_name")
    private String exchangeName;
    /**
     * routing_key
     */
    @TableField(value="routing_key")
    private String routingKey;
    /**
     * queue_name
     */
    @TableField(value="queue_name")
    private String queueName;
    /**
     * create_time
     */
    @TableField(value="create_time")
    private LocalDateTime createTime;
    /**
     * status
     */
    @TableField(value="status")
    private Integer status = 0;
    /**
     * sent_num
     */
    @TableField(value="sent_num")
    private Integer sentNum = 0;

    public MqSendTask() {

    }
}
