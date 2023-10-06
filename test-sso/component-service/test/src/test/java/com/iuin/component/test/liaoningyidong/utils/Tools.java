package com.iuin.component.test.liaoningyidong.utils;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("ALL")
@Slf4j
public class Tools {

    public static <T> T objectToJson(Object object, Class<T> cls) {
        String jsonStr = JSONUtil.toJsonStr(object);
        return JSONUtil.toBean(jsonStr, cls);
    }


}
