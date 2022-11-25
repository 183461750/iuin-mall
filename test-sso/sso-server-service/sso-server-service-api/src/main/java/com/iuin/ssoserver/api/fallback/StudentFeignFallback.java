package com.iuin.ssoserver.api.fallback;

import com.iuin.common.utils.RespResult;
import com.iuin.common.utils.resp.ResponseCode;
import com.iuin.ssoserver.api.feign.StudentFeign;
import com.iuin.ssoserver.api.model.req.StudentReq;
import com.iuin.ssoserver.api.model.resp.StudentResp;
import org.springframework.stereotype.Component;

/**
 * 学生相关信息查询接口Fallback
 *
 * @author Fa
 */
@Component
public class StudentFeignFallback implements StudentFeign {

    @Override
    public RespResult<StudentResp> studentInfo(StudentReq studentReq) {
        return RespResult.fail(ResponseCode.SERVICE_SSO_SERVER_ERROR);
    }

}
