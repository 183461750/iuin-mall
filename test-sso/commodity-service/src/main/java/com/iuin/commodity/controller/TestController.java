package com.iuin.commodity.controller;

import com.iuin.common.utils.RespResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fa
 */
@RestController
@RequestMapping("/commodity/test")
public class TestController {

    @RequestMapping("/get")
    public RespResult<?> get() {
        return RespResult.success();
    }

}
