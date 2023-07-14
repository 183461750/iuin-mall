package com.iuin.workflow;

import com.iuin.common.EnableCommonService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fa
 */
@EnableCommonService
@SpringBootApplication
//@EnableProcessApplication("workflow-service")
public class WorkflowServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkflowServiceApplication.class, args);
    }

}
