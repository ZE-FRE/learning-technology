package cn.zefre.rabbitmq;

import cn.zefre.rabbitmq.idempotence.annotation.EnableRabbitIdempotence;
import cn.zefre.rabbitmq.idempotence.service.RedisIdempotenceService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author pujian
 * @date 2022/8/12 16:14
 */
@SpringBootApplication
@MapperScan("cn.zefre.rabbitmq.mapper")
@EnableRabbitIdempotence(serviceProvider = RedisIdempotenceService.class)
public class RabbitApplicationLauncher {
    public static void main(String[] args) {
        SpringApplication.run(RabbitApplicationLauncher.class);
    }
}
