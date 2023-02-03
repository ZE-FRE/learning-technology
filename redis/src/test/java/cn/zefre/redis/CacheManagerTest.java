package cn.zefre.redis;

import org.junit.jupiter.api.Test;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pujian
 * @date 2023/2/3 17:34
 */
@Import(CacheManagerTest.UserService.class)
public class CacheManagerTest extends BaseRedisTest {

    @Resource
    private UserService userService;

    /**
     * @author pujian
     * @date 2023/2/3 17:43
     */
    public static class UserService {

        @Cacheable(cacheNames = "usernameList")
        public List<String> usernameList() {
            System.out.println("缓存失效");
            List<String> names = new ArrayList<>();
            names.add("张三");
            names.add("张三");
            names.add("张三");
            return names;
        }

        @Cacheable(cacheNames = "username")
        public String username(String id, String username) {
            return id + username;
        }
    }

    @Test
    public void testCacheList() {
        System.out.println(userService.usernameList());
    }

    @Test
    public void testCache() {
        System.out.println(userService.username("123", "zhangsan"));
    }

}
