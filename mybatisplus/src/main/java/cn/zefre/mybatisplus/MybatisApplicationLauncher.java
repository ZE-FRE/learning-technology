package cn.zefre.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author pujian
 * @date 2021/10/19 16:39
 */
@SpringBootApplication
@MapperScan("cn.zefre.**.mapper")
public class MybatisApplicationLauncher {

    public static void main(String[] args) {
        SpringApplication.run(MybatisApplicationLauncher.class);
    }
}
