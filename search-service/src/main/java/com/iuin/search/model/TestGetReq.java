package com.iuin.search.model;

import com.iuin.common.model.req.CommonPageReq;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fa
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TestGetReq extends CommonPageReq {

    private String test;
    /**
     * 是否需要高亮
     */
    @NotNull(message = "是否需要高亮字段不能为null")
    private Boolean isHighlight;

}
