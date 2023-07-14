package com.iuin.ssoserver.api.feign;

import com.iuin.common.utils.RespResult;
import com.iuin.ssoserver.api.fallback.StudentFeignFallback;
import com.iuin.ssoserver.api.model.req.StudentReq;
import com.iuin.ssoserver.api.model.resp.StudentResp;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 任务执行、流程步骤查询接口
 *
 * @author Fa
 */
@FeignClient(name = "sso-server-service", fallback = StudentFeignFallback.class)
public interface StudentFeign {

    /**
     * 查询学生信息
     *
     * @param studentReq 接口参数
     * @return 启动结果
     */
    @GetMapping(value = "/student/info")
    RespResult<StudentResp> studentInfo(@RequestBody @Valid StudentReq studentReq);

}
