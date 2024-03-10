package com.iuin.workflow.controller;

import com.iuin.workflow.model.dto.ProcessDefDTO;
import com.iuin.workflow.model.req.TerminateTaskReq;
import com.iuin.workflow.service.ProcessService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 审批流程相关
 *
 * @author fa
 */
@Slf4j
@RestController
@RequestMapping("/process")
public class ProcessController {

    @Resource
    private ProcessService processService;

    /**
     * 终止流程
     */
    @PostMapping(value = "/terminateTask")
    public ResponseEntity<ProcessDefDTO> terminateTask(@RequestBody @Valid TerminateTaskReq request) {
        ProcessDefDTO processDefDTO = processService.terminateTask(request);

        return ResponseEntity.ok(processDefDTO);
    }


}
