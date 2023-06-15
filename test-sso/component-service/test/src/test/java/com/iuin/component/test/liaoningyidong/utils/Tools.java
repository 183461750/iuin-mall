package com.iuin.component.test.liaoningyidong.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("ALL")
@Slf4j
public class Tools {

	public static <T> T objectToJson(Object object, Class<T> cls) {
		String jsonStr = JSON.toJSONString(object);
		return JSON.parseObject(jsonStr, cls);
	}



}
