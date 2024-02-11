package com.iuin.mall.domain.commodity_sales_area.commoditysalesarea;

import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.create.CommoditySalesAreaCreateCmd;

/**
 * 商品销售区域模版-聚合根-工厂
 */
@Component
public class CommoditySalesAreaTemplateFactory {

    public CommoditySalesAreaTemplate getInstance(CommoditySalesAreaCreateCmd createCmd) {
        CommoditySalesAreaTemplate instance = new CommoditySalesAreaTemplate();
        instance.setName(createCmd.getName());
        return instance;
    }

}
