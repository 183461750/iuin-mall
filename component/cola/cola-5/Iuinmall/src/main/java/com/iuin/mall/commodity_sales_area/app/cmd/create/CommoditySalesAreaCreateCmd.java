package com.iuin.mall.commodity_sales_area.app.cmd.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import com.iuin.mall.commodity_sales_area.domain.commoditysalesarea.*;

/**
 * 新增商品销售区域-指令
 *
 * @author visual-ddd
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommoditySalesAreaCreateCmd {

    /** 名称 */
    private String name;

}