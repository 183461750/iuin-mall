package com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.modify;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.*;

/**
 * 编辑商品销售区域-指令
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommoditySalesAreaModifyCmd {

    /** 商品销售区域 ID */
    private Long id;

    /** 名称 */
    private String name;

}