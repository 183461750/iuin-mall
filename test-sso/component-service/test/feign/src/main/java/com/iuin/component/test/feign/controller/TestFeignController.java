package com.iuin.component.test.feign.controller;

import com.iuin.common.utils.RespResult;
import com.iuin.component.test.feign.controller.feign.TestFeign;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fa
 */
@RestController
@RequestMapping(TestFeign.PATH_PREFIX)
public class TestFeignController implements TestFeign {

    @Override
    public RespResult<Void> get() {
        return RespResult.success();
    }
}
