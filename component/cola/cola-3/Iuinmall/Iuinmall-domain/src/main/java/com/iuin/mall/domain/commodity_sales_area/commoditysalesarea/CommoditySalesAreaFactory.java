package com.iuin.mall.domain.commodity_sales_area.commoditysalesarea;

import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.create.CommoditySalesAreaCreateCmd;

/**
 * 商品销售区域-聚合根-工厂
 */
@Component
public class CommoditySalesAreaFactory {

    public CommoditySalesArea getInstance(CommoditySalesAreaCreateCmd createCmd) {
        CommoditySalesArea instance = new CommoditySalesArea();
        instance.setName(createCmd.getName());
        return instance;
    }

}
