package com.iuin.workflow;

import com.iuin.component.base.EnableBaseComponent;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fa
 */
@EnableBaseComponent
@SpringBootApplication
//@EnableProcessApplication("workflow-service")
public class WorkflowServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkflowServiceApplication.class, args);
    }

}
