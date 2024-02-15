package com.iuin.b.controller;

import cn.dev33.satoken.sign.SaSignUtil;
import cn.dev33.satoken.sso.SaSsoUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    // 查询我的账号信息
    @RequestMapping("/sso/myFollowList")
    public Object myFollowList() {
        // 请求地址
        String url = "http://127.0.0.1:9000/test/sso/getFollowList";
        // 请求参数
        Map<String, Object> paramMap = new LinkedHashMap<>();
        paramMap.put("loginId", StpUtil.getLoginId());
        paramMap.put("userId", 10001);
        paramMap.put("money", 1000);

        // 补全 timestamp、nonce、sign 参数，并序列化为 kv 字符串
        String paramStr = SaSignUtil.addSignParamsAndJoin(paramMap);

        // 将参数字符串拼接在请求地址后面
        url += "?" + paramStr;

        // 调用，并返回 SaResult 结果
        SaResult res = SaSsoUtil.request(url);

        // 返回给前端
        return res;
    }

    @RequestMapping("/demo")
    public Object testDemo(String str) {

        // 返回给前端
        return SaResult.data(str);
    }

}
