package com.iuin.testssoserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TestSsoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestSsoServerApplication.class, args);
    }

}
