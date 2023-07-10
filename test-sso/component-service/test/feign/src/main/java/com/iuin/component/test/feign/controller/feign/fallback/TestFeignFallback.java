package com.iuin.component.test.feign.controller.feign.fallback;

import com.iuin.common.utils.RespResult;
import com.iuin.component.test.feign.controller.feign.TestFeign;
import org.springframework.stereotype.Component;

/**
 * @author fa
 */
@Component
public class TestFeignFallback implements TestFeign {

    @Override
    public RespResult<Void> get() {
        return RespResult.failFeignService("Test Feign service ex");
    }

}
