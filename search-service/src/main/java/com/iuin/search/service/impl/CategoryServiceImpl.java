package com.iuin.search.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.iuin.common.utils.RespResult;
import com.iuin.common.utils.RespUtil;
import com.iuin.search.api.model.req.CategoryReq;
import com.iuin.search.service.ICategoryService;
import com.iuin.ssoserver.api.feign.StudentFeign;
import com.iuin.ssoserver.api.model.req.StudentReq;
import com.iuin.search.api.model.resp.CategoryResp;
import com.iuin.ssoserver.api.model.resp.StudentResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author fa
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final StudentFeign studentFeign;

    @Override
    public CategoryResp info(CategoryReq categoryReq) {
        RespResult<StudentResp> respResult = studentFeign.studentInfo(StudentReq.builder().name(categoryReq.getName()).build());
        StudentResp studentResp = RespUtil.getDataOrThrow(respResult);

        return BeanUtil.copyProperties(studentResp, CategoryResp.class);
    }
}
