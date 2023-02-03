package cn.zefre.redis;

import cn.zefre.redis.entity.User;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author pujian
 * @date 2022/9/23 11:14
 */
public class CrudTest extends BaseRedisTest {

    @Test
    public void testSetString() {
        User user = new User(1L, "张三");
        redisTemplate.opsForValue().set("redis:user:1", user, 100, TimeUnit.SECONDS);
    }

    @Test
    public void testReadString() {
        System.out.println(redisTemplate.opsForValue().get("redis:user:1"));
    }

    @Test
    public void testDelete() {
        redisTemplate.delete("redis:user:1");
    }

}
