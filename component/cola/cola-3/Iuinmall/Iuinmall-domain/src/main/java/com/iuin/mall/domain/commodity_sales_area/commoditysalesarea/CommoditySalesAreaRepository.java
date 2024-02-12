package com.iuin.mall.domain.commodity_sales_area.commoditysalesarea;

/**
 * 商品销售区域-聚合根-仓储接口
 */
public interface CommoditySalesAreaRepository {

    CommoditySalesArea save(CommoditySalesArea commoditySalesArea);

    CommoditySalesArea update(CommoditySalesArea commoditySalesArea);

    void remove(CommoditySalesArea commoditySalesArea);

    CommoditySalesArea find(Long id);

}
