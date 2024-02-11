package com.iuin.mall.domain.commodity_sales_area.commoditysalesarea;

import lombok.Data;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;

/**
 * 商品销售区域-聚合根
 */
@Data
public abstract class AbstractCommoditySalesArea {

    /** 商品销售区域 ID */
    private Long id;

    /** 名称 */
    private String name;

}