package com.iuin.commodity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fa
 */
//@MapperScan("com.baomidou.mybatisplus.samples.quickstart.mapper")
@MapperScan("com.iuin.commodity.mapper")
@SpringBootApplication
public class CommodityServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommodityServiceApplication.class, args);
    }

}
