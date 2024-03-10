package com.iuin.workflow.model.dto;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * 流程定义列表
 *
 * @author fa
 */
@Data
@FieldNameConstants(asEnum = true)
public class ProcessDefListDTO {

    /**
     * 弹窗计划
     */
    private ProcessDefDTO popupPlanAudit;

    /**
     * 启动页
     */
    private ProcessDefDTO startUpAudit;

    /**
     * 模版
     */
    private ProcessDefDTO mallTemplateAudit;

}
