package cn.zefre.redis;

import cn.zefre.redis.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author pujian
 * @date 2022/9/23 11:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplicationLauncher.class)
public class StringTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testSet() {
        User user = new User(1L, "张三");
        redisTemplate.opsForValue().set("redis:user:1", user, 10, TimeUnit.SECONDS);
    }

}
