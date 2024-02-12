package com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.remove;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.*;

/**
 * 删除商品销售区域-指令
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommoditySalesAreaRemoveCmd {

    /** 商品销售区域 ID */
    private Long id;

}