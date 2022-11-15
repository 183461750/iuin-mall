package com.example.demoserver.controller;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.sso.SaSsoUtil;
import cn.dev33.satoken.util.SaResult;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("/demo")
    public JSONObject demo(String test) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("1", 1);
        jsonObject.put("test", test);
        return jsonObject;
    }

    @GetMapping("/demo2")
    public JSONObject demo2(String test) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("1", 1);
        jsonObject.put("test", test);
        log.info("test: {}", test);
        return jsonObject;
    }



    // 获取指定用户的关注列表
    @RequestMapping("/sso/getFollowList")
    public Object ssoRequest(Long loginId) {

        // 校验签名，签名不通过直接抛出异常
        SaSsoUtil.checkSign(SaHolder.getRequest());

        // 查询数据 (此处仅做模拟)
        List<Integer> list = Arrays.asList(10041, 10042, 10043, 10044);

        // 返回
        return SaResult.data(list);
    }

}
