package com.iuin.b;

import com.iuin.component.base.annos.EnableBaseComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author fa
 */
@EnableBaseComponent
@EnableFeignClients(basePackages = {"com.iuin"})
@EnableDiscoveryClient
@SpringBootApplication
public class BServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BServiceApplication.class, args);
    }

}
