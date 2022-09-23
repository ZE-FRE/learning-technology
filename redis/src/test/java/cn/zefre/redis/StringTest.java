package cn.zefre.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pujian
 * @date 2022/9/23 11:14
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplicationLauncher.class)
public class StringTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testSet() {
        Map<String, Object> user = new HashMap<>();
        user.put("id", 1);
        user.put("name", "张三");
        redisTemplate.opsForValue().set("redis:user:1", user, 1000 * 2);
    }

    @Test
    public void testGet() {
        @SuppressWarnings("unchecked")
        Map<String, Object> user = (Map<String, Object>) redisTemplate.opsForValue().get("redis:user:1");
        System.out.println(user);
    }

}
