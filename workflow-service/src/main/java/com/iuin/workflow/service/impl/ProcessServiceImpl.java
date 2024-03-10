package com.iuin.workflow.service.impl;

import com.iuin.component.base.exceptions.BusinessException;
import com.iuin.workflow.common.enums.ProcessDefEnum;
import com.iuin.workflow.config.AppProcessConfig;
import com.iuin.workflow.model.dto.ProcessDefDTO;
import com.iuin.workflow.model.req.TerminateTaskReq;
import com.iuin.workflow.service.ProcessService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 审批流相关接口
 *
 * @author fa
 */
@Slf4j
@Service
public class ProcessServiceImpl implements ProcessService {

    @Resource
    private AppProcessConfig appProcessConfig;

    @Override
    public ProcessDefDTO terminateTask(TerminateTaskReq request) {
        // 查询流程定义配置对象
        ProcessDefDTO processDef = ProcessDefEnum.findProcessDef(appProcessConfig.getProcessDefList(), request.getBizType());
        if (Objects.isNull(processDef)) {
            throw new BusinessException("业务类型不存在");
        }

        return processDef;
    }
}
