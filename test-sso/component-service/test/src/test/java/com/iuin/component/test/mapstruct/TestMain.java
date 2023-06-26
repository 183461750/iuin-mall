package com.iuin.component.test.mapstruct;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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
