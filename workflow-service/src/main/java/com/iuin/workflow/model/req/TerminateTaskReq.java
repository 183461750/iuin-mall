package com.iuin.workflow.model.req;

import com.iuin.workflow.model.dto.ProcessDefListDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 终止流程
 *
 * @author fa
 */
@Data
public class TerminateTaskReq {

    /**
     * 业务类型: popupPlanAudit:弹窗计划 startUpAudit:启动页 mallTemplateAudit:模版
     *
     * @see ProcessDefListDTO.Fields
     */
    @NotNull(message = "业务类型不能为空")
    private ProcessDefListDTO.Fields bizType;

}
