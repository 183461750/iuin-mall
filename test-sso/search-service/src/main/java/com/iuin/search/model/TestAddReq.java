package com.iuin.search.model;

import com.iuin.common.model.CommonPage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 *
 * @author fa
 */
@Data
public class TestAddReq {

    /**
     * 商品名
     */
    @NotNull(message = "商品名称不能为null")
    private String name;
    /**
     * 最小价格
     */
    private BigDecimal min;
    /**
     * 最大价格
     */
    private BigDecimal max;
    /**
     * 测试字段
     */
    private String test;

}
