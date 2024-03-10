package com.iuin.workflow.common.enums;

import cn.hutool.core.collection.CollUtil;
import com.iuin.workflow.model.dto.ProcessDefDTO;
import com.iuin.workflow.model.dto.ProcessDefListDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Function;

/**
 * BPM的流程定义配置
 *
 * @author Fa
 */
@Getter
@RequiredArgsConstructor
public enum ProcessDefEnum {

    POPUP_PLAN(ProcessDefListDTO.Fields.popupPlanAudit, ProcessDefListDTO::getPopupPlanAudit, "弹窗计划审核"),

    MALL_TEMPLATE(ProcessDefListDTO.Fields.mallTemplateAudit, ProcessDefListDTO::getMallTemplateAudit, "模板装修审核"),

    START_UP(ProcessDefListDTO.Fields.startUpAudit, ProcessDefListDTO::getStartUpAudit, "启动页计划审核"),

    ;

    /**
     * 业务类型
     */
    private final ProcessDefListDTO.Fields bizType;
    /**
     * 业务配置对象获取函数式接口
     */
    private final Function<ProcessDefListDTO, ProcessDefDTO> func;
    /**
     * 流程定义名
     */
    private final String defName;

    private static final List<ProcessDefEnum> LIST = CollUtil.toList(values());

    /**
     * 通过业务类型获取流程定义配置对象
     */
    public static ProcessDefDTO findProcessDef(ProcessDefListDTO processDefList, ProcessDefListDTO.Fields bizType) {
        return LIST.stream().filter(o -> o.getBizType().equals(bizType)).findFirst().map(ProcessDefEnum::getFunc).map(o -> o.apply(processDefList)).orElse(null);
    }

    /**
     * 通过业务类型获取流程定义名称
     */
    public static String findProcessDefName(ProcessDefListDTO.Fields bizType) {
        return LIST.stream().filter(o -> o.getBizType().equals(bizType)).findFirst().map(ProcessDefEnum::getDefName).orElse("");
    }

}
