package com.iuin.mall.commodity_sales_area.domain.commoditysalesarea;

/**
 * 商品销售区域模版-聚合根-仓储接口
 *
 * @author visual-ddd
 * @since 1.0
 */
public interface CommoditySalesAreaTemplateRepository {

    CommoditySalesAreaTemplate save(CommoditySalesAreaTemplate commoditySalesAreaTemplate);

    CommoditySalesAreaTemplate update(CommoditySalesAreaTemplate commoditySalesAreaTemplate);

    void remove(CommoditySalesAreaTemplate commoditySalesAreaTemplate);

    CommoditySalesAreaTemplate find(Long id);

}
