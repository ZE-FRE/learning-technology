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
 * 已消费的幂等消息记录
 *
 * @author pujian
 * @date 2022/9/19 13:17
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@TableName("t_mq_idempotence_message")
public class MqIdempotenceMessage {

    /**
     * 消息id
     */
    @TableId(value = "message_id", type = IdType.ASSIGN_UUID)
    private String messageId;

    @TableField(value="create_time")
    private LocalDateTime createTime;

}
