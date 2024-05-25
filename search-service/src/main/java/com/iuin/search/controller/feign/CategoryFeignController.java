package com.iuin.search.controller.feign;

import com.iuin.common.utils.RespResult;
import com.iuin.search.api.model.req.CategoryReq;
import com.iuin.search.service.ICategoryService;
import com.iuin.search.api.feign.CategoryFeign;
import com.iuin.search.api.model.resp.CategoryResp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fa
 */
@RestController
@RequiredArgsConstructor
public class CategoryFeignController implements CategoryFeign {

    private final ICategoryService categoryService;

    @Override
    public RespResult<CategoryResp> info(@RequestBody @Valid CategoryReq req) {
        return RespResult.success(categoryService.info(req));
    }

}
