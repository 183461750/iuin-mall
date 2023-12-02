package com.iuin.component.test.mysql.controller;

import com.iuin.common.utils.RespResult;
import com.iuin.component.test.mysql.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fa
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/get")
    public RespResult<Void> get() {
        return RespResult.success();
    }

    /**
     * 插入数据
     */
    @GetMapping("/insertDate")
    public RespResult<Boolean> insertDate() {
        return RespResult.success(testService.insertDate());
    }

}
