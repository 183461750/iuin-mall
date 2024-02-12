package com.iuin.mall.start;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.wakedata.common.mq.annotation.EnableMessageCenter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * iuin-mall-启动类
 */
@EnableMessageCenter
@SpringBootApplication(scanBasePackages = {"com.iuin.mall"})
@MapperScan("com.iuin.mall.*.infrastructure.repository.mapper")
@EntityScan(basePackages = {"com.iuin.mall.infrastructure.*.repository.model"})
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

}
