package com.iuin.b.controller;

import cn.dev33.satoken.sso.SaSsoUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    // 查询我的账号信息
    @RequestMapping("/sso/myFollowList")
    public Object myFollowList() {
        // 组织url，加上签名参数
        String url = SaSsoUtil.addSignParams("http://127.0.0.1:9000/test/sso/getFollowList", StpUtil.getLoginId());

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
