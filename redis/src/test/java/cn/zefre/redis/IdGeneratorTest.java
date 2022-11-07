package cn.zefre.redis;

import cn.zefre.redis.config.IdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author pujian
 * @date 2022/9/24 16:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplicationLauncher.class)
public class IdGeneratorTest {

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
