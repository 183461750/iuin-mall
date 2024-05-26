package com.iuin.search.api.feign;

import com.iuin.common.constants.ModuleConstant;
import com.iuin.common.utils.RespResult;
import com.iuin.search.api.fallback.CategoryFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author huan.fu
 * @since 2023/6/19 - 22:21
 */
@Primary
@FeignClient(name = ModuleConstant.SEARCH_SERVICE, fallback = CategoryFeignFallback.class)
public interface ProviderFeign {

    /**
     * 接口前缀
     */
    String PATH_PREFIX = ModuleConstant.SEARCH_FEIGN_PATH_PREFIX + "/provider";

    /**
     * 获取服务信息
     *
     * @return ip:port
     */
    @GetMapping(PATH_PREFIX + "/serverInfo")
    RespResult<String> fetchServerInfo();

}