package com.iuin.commodity.service;

import cn.hutool.core.bean.BeanUtil;
import com.iuin.commodity.model.resp.Test1Resp;
import com.iuin.common.utils.RespResult;
import com.iuin.common.utils.RespUtil;
import com.iuin.search.api.feign.CategoryFeign;
import com.iuin.search.api.model.req.CategoryReq;
import com.iuin.search.api.model.resp.CategoryResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author fa
 */
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements ITestService {

    private final CategoryFeign categoryFeign;


    @Override
    public Test1Resp test1(String str) {
        RespResult<CategoryResp> respResult = categoryFeign.info(CategoryReq.builder().name(str).build());
        CategoryResp categoryResp = RespUtil.getDataOrThrow(respResult);
        return BeanUtil.copyProperties(categoryResp, Test1Resp.class);
    }
}
