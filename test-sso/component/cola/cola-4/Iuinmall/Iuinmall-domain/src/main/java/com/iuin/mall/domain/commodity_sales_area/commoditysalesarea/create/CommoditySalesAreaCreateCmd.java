package com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.*;

/**
 * 新增商品销售区域-指令
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommoditySalesAreaCreateCmd {

    /** 名称 */
    private String name;

}