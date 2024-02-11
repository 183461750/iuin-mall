package com.iuin.mall.domain.commodity_sales_area.commoditysalesarea;

/**
 * 商品销售区域模版-聚合根-仓储接口
 */
public interface CommoditySalesAreaTemplateRepository {

    CommoditySalesAreaTemplate save(CommoditySalesAreaTemplate commoditySalesAreaTemplate);

    CommoditySalesAreaTemplate update(CommoditySalesAreaTemplate commoditySalesAreaTemplate);

    void remove(CommoditySalesAreaTemplate commoditySalesAreaTemplate);

    CommoditySalesAreaTemplate find(Long id);

}
