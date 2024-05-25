package com.iuin.ssoserver.api.feign;

import com.iuin.common.constants.ModuleConstant;
import com.iuin.common.utils.RespResult;
import com.iuin.ssoserver.api.fallback.StudentFeignFallback;
import com.iuin.ssoserver.api.model.req.StudentReq;
import com.iuin.ssoserver.api.model.resp.StudentResp;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 任务执行、流程步骤查询接口
 *
 * @author Fa
 */
@Primary
@FeignClient(name = ModuleConstant.SSO_SERVER_SERVICE, fallback = StudentFeignFallback.class)
public interface StudentFeign {

    /**
     * 接口前缀
     */
    String PATH_PREFIX = ModuleConstant.SSO_SERVER_FEIGN_PATH_PREFIX + "/student";

    /**
     * 查询学生信息
     *
     * @param studentReq 接口参数
     * @return 启动结果
     */
    @PostMapping(value = PATH_PREFIX + "/info")
    RespResult<StudentResp> studentInfo(@RequestBody @Valid StudentReq studentReq);

}
