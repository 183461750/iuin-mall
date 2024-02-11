package com.iuin.mall.commodity_sales_area.domain.commoditysalesarea;

import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import com.iuin.mall.commodity_sales_area.app.cmd.create.CommoditySalesAreaCreateCmd;

/**
 * 商品销售区域模版-聚合根-工厂
 *
 * @author visual-ddd
 * @since 1.0
 */
@Component
public class CommoditySalesAreaTemplateFactory {

    public CommoditySalesAreaTemplate getInstance(CommoditySalesAreaCreateCmd createCmd) {
        CommoditySalesAreaTemplate instance = new CommoditySalesAreaTemplate();
        instance.setName(createCmd.getName());
        return instance;
    }

}
