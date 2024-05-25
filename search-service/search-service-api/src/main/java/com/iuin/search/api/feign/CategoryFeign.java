package com.iuin.search.api.feign;

import com.iuin.common.constants.ModuleConstant;
import com.iuin.common.utils.RespResult;
import com.iuin.search.api.fallback.CategoryFeignFallback;
import com.iuin.search.api.model.req.CategoryReq;
import com.iuin.search.api.model.resp.CategoryResp;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Fa
 */
@Primary
@FeignClient(name = ModuleConstant.SEARCH_SERVICE, fallback = CategoryFeignFallback.class)
public interface CategoryFeign {

    /**
     * 接口前缀
     */
    String PATH_PREFIX = ModuleConstant.SEARCH_FEIGN_PATH_PREFIX + "/category";

    /**
     * 查询信息
     */
    @PostMapping(value = PATH_PREFIX + "/info")
    RespResult<CategoryResp> info(@RequestBody @Valid CategoryReq req);

}
