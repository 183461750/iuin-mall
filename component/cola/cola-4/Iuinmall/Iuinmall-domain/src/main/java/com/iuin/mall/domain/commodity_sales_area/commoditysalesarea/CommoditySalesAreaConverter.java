package com.iuin.mall.domain.commodity_sales_area.commoditysalesarea;

import cn.hutool.json.JSONUtil;

import java.util.List;

/**
 * 商品销售区域-实体JSON转换器
 */
public class CommoditySalesAreaConverter {

    public String entity2Json(CommoditySalesArea entity) {
        return JSONUtil.toJsonStr(entity);
    }

    public CommoditySalesArea json2Entity(String value) {
        return JSONUtil.toBean(value, CommoditySalesArea.class);
    }

    public String list2json(List<CommoditySalesArea> entityList) {
        return JSONUtil.toJsonStr(entityList);
    }

    public List<CommoditySalesArea> json2list(String value) {
        return JSONUtil.toList(value, CommoditySalesArea.class);
    }

}