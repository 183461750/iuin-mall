package com.iuin.mall.commodity_sales_area.domain.commoditysalesarea;

import lombok.Data;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import com.iuin.mall.commodity_sales_area.app.cmd.modify.CommoditySalesAreaModifyCmd;
import com.iuin.mall.commodity_sales_area.app.cmd.remove.CommoditySalesAreaRemoveCmd;

/**
 * 商品销售区域模版-聚合根
 *
 * @author visual-ddd
 * @since 1.0
 */
@Data
public class CommoditySalesAreaTemplate {

    /** 商品销售区域 ID */
    private Long id;

    /** 名称 */
    private String name;

    /** 商品销售区域模版 */
    private List<CommoditySalesArea> commoditySalesAreaTemplate;

    public void modify(CommoditySalesAreaModifyCmd updateCmd) {
        this.setId(updateCmd.getId());
        this.setName(updateCmd.getName());
    }

    public void remove(CommoditySalesAreaRemoveCmd removeCmd) {
        this.setId(removeCmd.getId());
    }

}