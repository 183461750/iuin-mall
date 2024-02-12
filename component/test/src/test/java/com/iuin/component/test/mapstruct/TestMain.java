package com.iuin.component.test.mapstruct;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * @author fa
 */
@Component
public class TestMain {

    @PostConstruct
    public void postConstruct() {
        System.out.println("hello world!");
    }

}
