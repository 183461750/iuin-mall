package com.iuin.ssoserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author fa
 */
@EnableJpaAuditing
@EnableDiscoveryClient
@SpringBootApplication
public class SsoServerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoServerServiceApplication.class, args);
    }

}
