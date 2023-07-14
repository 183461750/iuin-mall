package com.iuin.search.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
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
