package com.iuin.agent.controller;

import com.iuin.agent.service.ITestService;
import com.iuin.common.utils.RespResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 *
 * @author fa
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final ITestService testService;

    /**
     * 根据名称查询id
     */
    @GetMapping("/findIdByName")
    public RespResult<String> findIdByName(@RequestParam String name) {
        return RespResult.success(testService.findIdByName(name));
    }

}
