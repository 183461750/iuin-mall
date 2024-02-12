package com.iuin.search.model;

import com.iuin.common.model.CommonPage;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fa
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TestGetReq extends CommonPage {

    private String test;
    /**
     * 是否需要高亮
     */
    @NotNull(message = "是否需要高亮字段不能为null")
    private Boolean isHighlight;

}
