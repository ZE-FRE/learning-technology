package cn.zefre.redis;

import cn.zefre.redis.config.RedisConfig;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * slice测试得找到{@link SpringBootConfiguration}或{@link SpringBootApplication}标注的类，才能开始测试
 * slice测试不会对@Configuration里的@Bean生效，所以需要手动import{@link RedisConfig}
 *
 * 注意：如果找到多个{@link SpringBootConfiguration}，则会报错；
 * 若这些{@link SpringBootConfiguration}在各自的包下，启动slice只扫描到它自己那个，则不会报错
 * 例如：cn.zefre.redis.package1下有个{@link SpringBootConfiguration}，cn.zefre.redis.package2下也有一个，
 * 那么package1和package2下面的slice测试都不会报错
 * {@link org.springframework.boot.test.context.SpringBootTest}和slice同理
 *
 * @author pujian
 * @date 2023/2/3 16:05
 */
@DataRedisTest
@SpringBootConfiguration
@EnableAutoConfiguration
@Import({RedisConfig.class, JacksonAutoConfiguration.class})
public class BaseRedisTest {

    @Resource
    protected RedisTemplate<String, Object> redisTemplate;

}
