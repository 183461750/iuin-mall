package com.iuin.search.api.fallback;

import com.iuin.common.enums.ResponseCodeEnum;
import com.iuin.common.utils.RespResult;
import com.iuin.search.api.model.req.CategoryReq;
import com.iuin.search.api.model.resp.CategoryResp;
import com.iuin.search.api.feign.CategoryFeign;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 学生相关信息查询接口Fallback
 *
 * @author Fa
 */
@Component
public class CategoryFeignFallback implements CategoryFeign {

    @Override
    public RespResult<CategoryResp> info(@RequestBody @Valid CategoryReq req) {
        return RespResult.fail(ResponseCodeEnum.SEARCH_SERVER_ERROR);
    }

}
