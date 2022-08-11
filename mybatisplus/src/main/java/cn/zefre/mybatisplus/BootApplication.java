package cn.zefre.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author pujian
 * @date 2021/10/19 16:39
 */
@SpringBootApplication(scanBasePackages = {"cn.zefre"})
@MapperScan("cn.zefre.**.mapper")
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class);
    }
}
