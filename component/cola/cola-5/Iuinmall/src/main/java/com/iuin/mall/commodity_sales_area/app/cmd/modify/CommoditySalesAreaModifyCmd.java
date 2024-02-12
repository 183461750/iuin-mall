package com.iuin.mall.commodity_sales_area.app.cmd.modify;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import com.iuin.mall.commodity_sales_area.domain.commoditysalesarea.*;

/**
 * 编辑商品销售区域-指令
 *
 * @author visual-ddd
 * @since 1.0
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