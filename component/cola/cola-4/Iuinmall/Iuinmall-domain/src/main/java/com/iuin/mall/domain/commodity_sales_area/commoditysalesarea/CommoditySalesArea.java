package com.iuin.mall.domain.commodity_sales_area.commoditysalesarea;

import lombok.Data;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;

/**
 * 商品销售区域-实体
 */
@Data
public class CommoditySalesArea {

    /** 商品销售区域 ID */
    public Long id;

    /** 名称 */
    private String name;

    /** 区域详情 */
    private AreaDetail areaDetail;

    /** 编码 */
    private String code;
}