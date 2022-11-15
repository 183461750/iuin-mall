package com.example.demoserver.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
