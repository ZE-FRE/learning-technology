package cn.zefre.redis.config;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author pujian
 * @date 2022/9/24 16:16
 */
@Component
public class IdGenerator {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private static final int COUNT_BITS = 32;

    private static final long BEGIN_TIMESTAMP = 1663977600;

    private static long toTimestamp() {
        LocalDateTime dateTime = LocalDateTime.of(2022, 9, 24, 0, 0, 0);
        return dateTime.toEpochSecond(ZoneOffset.UTC);
    }

    /**
     * 基于redis生成全局唯一id
     * 高32位为时间戳，低32位为从redis获取的序列号
     * 由于时间戳都是正的，所以最高位为0
     *
     * @param keyPrefix key前缀，可以根据业务来
     * @return 全局唯一id
     * @author pujian
     * @date 2022/9/24 16:24
     */
    public long nextId(String keyPrefix) {
        // 生成时间戳
        LocalDateTime now = LocalDateTime.now();
        long timestamp = now.toEpochSecond(ZoneOffset.UTC) - BEGIN_TIMESTAMP;
        // 开始生成序列号
        // 获取当前日期，精确到天
        String date = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        @SuppressWarnings("all")
        long count = redisTemplate.opsForValue().increment("incrementId:" + keyPrefix + ":" + date);
        // 拼接时间戳并返回
        return timestamp << COUNT_BITS | count;
    }

}
