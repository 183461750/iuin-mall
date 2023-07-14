package com.iuin.workflow.controller;

import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fa
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private IdentityService identityService;

    @Resource
    private TaskService taskService;


    @PostMapping("/deploy")
    public String deploy(@RequestParam String name, @RequestParam String fileName) {
        System.out.println("deploy-start");
        Deployment deployment = repositoryService.createDeployment()
                .name(name)
                .addClasspathResource(fileName)
                .deploy();
        System.out.println("流程id:" + deployment.getId());
        System.out.println("流程name:" + deployment.getName());
        return "deploy-start";
    }

    @GetMapping("/getDeploy")
    public String getDeploy(@RequestParam String deploymentId) {
        System.out.println("deploymentId:" + deploymentId);
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
        System.out.println("deployment:" + deployment);
        return "getDeploy end";
    }


    @PostMapping("/start")
    public String startInstance(String userId, String processDefinitionKey, String businessKey) {
        System.out.println("startInstance-start");
        Map<String, Object> map = new HashMap<>(3);
//        map.put("tony", "tony");
//        map.put("joe", "joe");
//        map.put("book", "book");
        map.put("sale", "zhangsan");
        identityService.setAuthenticatedUserId(userId);
//        String processDefinitionKey = "Process_0cfza8o";
//        String businessKey = "价格审批2";
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, map);

        System.out.println("processInstance:" + JSONUtil.toJsonStr(processInstance));
        return "startInstance-end";
    }

    @GetMapping("/todo")
    public String queryMyToDo(@RequestParam String userId) {
        System.out.println("startInstance-start");
        List<Task> taskList = new ArrayList<>();
        try {
            taskList = taskService.createTaskQuery().taskAssignee(userId).list();
            taskList.forEach(task -> System.out.println(task.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONUtil.toJsonStr(taskList);
    }

    @GetMapping("/taskPass")
    public String taskPass(@RequestParam String taskId) {
//        String id = "17546712-c63b-11ec-a6c5-327a20e038c0";
        taskService.complete(taskId);
        return "审批通过";
    }

}