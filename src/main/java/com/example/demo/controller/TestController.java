package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
