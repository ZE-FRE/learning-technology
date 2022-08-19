package cn.zefre.rabbitmq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author pujian
 * @date 2022/8/12 16:14
 */
@SpringBootApplication(scanBasePackages = {"cn.zefre"})
@MapperScan("cn.zefre.rabbitmq.mapper")
public class BootApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class);
    }
}
