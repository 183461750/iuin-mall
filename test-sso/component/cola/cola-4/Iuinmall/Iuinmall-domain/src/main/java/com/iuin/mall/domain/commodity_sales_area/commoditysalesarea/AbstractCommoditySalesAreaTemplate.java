package com.iuin.mall.domain.commodity_sales_area.commoditysalesarea;

import lombok.Data;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;

/**
 * 商品销售区域模版-聚合根
 */
@Data
public abstract class AbstractCommoditySalesAreaTemplate {

    /** 商品销售区域 ID */
    private Long id;

    /** 名称 */
    private String name;

    /** 商品销售区域模版 */
    private List<CommoditySalesArea> commoditySalesAreaTemplate;

}