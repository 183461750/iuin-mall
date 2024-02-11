package com.iuin.mall.domain.commodity_sales_area.commoditysalesarea;

import cn.hutool.json.JSONUtil;

import java.util.List;

/**
 * 区域详情-值对象JSON转换器
 */
public class UntitledConverter {

    public String valueObject2json(Untitled valueObject) {
        return JSONUtil.toJsonStr(valueObject);
    }

    public Untitled json2valueObject(String value) {
        return JSONUtil.toBean(value, Untitled.class);
    }

    public String list2json(List<Untitled> valueObjectList) {
        return JSONUtil.toJsonStr(valueObjectList);
    }

    public List<Untitled> json2list(String value) {
        return JSONUtil.toList(value, Untitled.class);
    }

}