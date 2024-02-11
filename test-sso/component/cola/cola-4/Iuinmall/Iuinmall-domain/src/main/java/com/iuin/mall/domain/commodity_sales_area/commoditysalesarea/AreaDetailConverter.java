package com.iuin.mall.domain.commodity_sales_area.commoditysalesarea;

import cn.hutool.json.JSONUtil;

import java.util.List;

/**
 * 区域详情-值对象JSON转换器
 */
public class AreaDetailConverter {

    public String valueObject2json(AreaDetail valueObject) {
        return JSONUtil.toJsonStr(valueObject);
    }

    public AreaDetail json2valueObject(String value) {
        return JSONUtil.toBean(value, AreaDetail.class);
    }

    public String list2json(List<AreaDetail> valueObjectList) {
        return JSONUtil.toJsonStr(valueObjectList);
    }

    public List<AreaDetail> json2list(String value) {
        return JSONUtil.toList(value, AreaDetail.class);
    }

}