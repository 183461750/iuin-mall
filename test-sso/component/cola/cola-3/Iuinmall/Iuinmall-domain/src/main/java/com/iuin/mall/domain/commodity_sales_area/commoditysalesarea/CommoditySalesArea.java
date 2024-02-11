package com.iuin.mall.domain.commodity_sales_area.commoditysalesarea;

import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.modify.CommoditySalesAreaModifyCmd;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.remove.CommoditySalesAreaRemoveCmd;

/**
 * 商品销售区域-聚合根能力
 */
public class CommoditySalesArea extends AbstractCommoditySalesArea {

    public void modify(CommoditySalesAreaModifyCmd updateCmd) {
        this.setId(updateCmd.getId());
        this.setName(updateCmd.getName());
    }

    public void remove(CommoditySalesAreaRemoveCmd removeCmd) {
        this.setId(removeCmd.getId());
    }
}