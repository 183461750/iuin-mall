package com.iuin.workflow.service;


import com.iuin.workflow.model.dto.ProcessDefDTO;
import com.iuin.workflow.model.req.TerminateTaskReq;

/**
 * 审批流相关接口
 *
 * @author fa
 */
public interface ProcessService {

    /**
     * 终止流程
     */
    ProcessDefDTO terminateTask(TerminateTaskReq request);

}
