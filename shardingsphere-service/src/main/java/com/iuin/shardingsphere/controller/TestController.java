package com.iuin.shardingsphere.controller;

import com.iuin.shardingsphere.service.ComUserService;
import com.iuin.common.utils.RespResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fa
 */
@RestController
@RequestMapping("/commodity/test")
@RequiredArgsConstructor
public class TestController {

    private final ComUserService comUserService;


    @PostMapping("/save")
    public RespResult<Void> save() {
        comUserService.save();
        return RespResult.success();
    }


    @RequestMapping("/get")
    public RespResult<Void> get() {
        return RespResult.success();
    }

}
