package com.iuin.commodity.controller;

import com.iuin.commodity.service.ComUserService;
import com.iuin.common.utils.RespResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fa
 */
@Slf4j
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

    @GetMapping("/get2")
    public ResponseEntity<String> get(String str) {
        log.info("测试: str:{}", str);
        return ResponseEntity.ok(str);
    }

}
