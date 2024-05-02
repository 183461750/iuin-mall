package com.iuin.commodity.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fa
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/get")
    public ResponseEntity<String> get(String str) {
        log.info("测试: str:{}", str);
        return ResponseEntity.ok(str);
    }

}
