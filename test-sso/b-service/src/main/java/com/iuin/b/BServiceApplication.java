package com.iuin.b;

import com.iuin.common.EnableCommonService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author fa
 */
@EnableCommonService
@EnableFeignClients(basePackages = {"com.iuin"})
@EnableDiscoveryClient
@SpringBootApplication
public class BServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BServiceApplication.class, args);
    }

}
