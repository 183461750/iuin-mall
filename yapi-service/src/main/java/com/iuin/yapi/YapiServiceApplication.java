package com.iuin.yapi;

import com.iuin.component.base.EnableBaseComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fa
 */
@SpringBootApplication
@EnableBaseComponent
public class YapiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(YapiServiceApplication.class, args);
    }

}
