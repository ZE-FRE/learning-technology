package cn.zefre.redis;

import cn.zefre.redis.generator.IdGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 单纯的slice测试，显示使用{@link ComponentScan}时，
 * 需要像{@link SpringBootApplication}一样设置{@link ComponentScan#excludeFilters()}
 *
 * @author pujian
 * @date 2022/9/24 16:52
 */
@ComponentScan(basePackages = "cn.zefre.redis",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class)})
public class IdGeneratorTest extends BaseRedisTest {

    @Resource
    private IdGenerator idGenerator;

    @Test
    public void testNextId() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Runnable task = () -> {
            long orderId = idGenerator.nextId("order");
            System.out.println(orderId);
            countDownLatch.countDown();
        };
        for (int i = 0; i < 100; i++) {
            executorService.submit(task);
        }
        countDownLatch.await();
    }

}
