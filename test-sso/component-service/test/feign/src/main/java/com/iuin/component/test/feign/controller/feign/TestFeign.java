package com.iuin.component.test.feign.controller.feign;

import com.iuin.common.utils.RespResult;
import com.iuin.component.test.feign.controller.feign.fallback.TestFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import static com.iuin.component.test.feign.controller.feign.TestFeign.PATH_PREFIX;

/**
 * @author fa
 */
@FeignClient(value = "manage-service", path = PATH_PREFIX, fallback = TestFeignFallback.class)
public interface TestFeign {

    String PATH_PREFIX = "/test";

    @GetMapping("/get")
    RespResult<Void> get();

}
