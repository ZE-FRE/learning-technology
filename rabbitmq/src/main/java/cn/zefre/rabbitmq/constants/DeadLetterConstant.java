package cn.zefre.rabbitmq.constants;

/**
 * 死信队列常量
 *
 * @author pujian
 * @date 2022/8/29 17:31
 */
public final class DeadLetterConstant {

    private DeadLetterConstant() {

    }
    /**
     * 死信队列交换机标识符
     */
    public static final String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
    /**
     * 死信队列交换机绑定键标识符
     */
    public static final String X_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";



    /**
     * 默认死信队列交换机
     */
    public static final String DEFAULT_DEAD_LETTER_EXCHANGE = "default.dead.letter.exchange";
    /**
     * 默认死信队列
     */
    public static final String DEFAULT_DEAD_LETTER_QUEUE = "default.dead.letter.queue";
    /**
     * 默认死信队列路由
     */
    public static final String DEFAULT_DEAD_LETTER_ROUTING = "default.dead.letter.routing";

}
