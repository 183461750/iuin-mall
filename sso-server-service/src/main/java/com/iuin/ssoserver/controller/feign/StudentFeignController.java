package com.iuin.ssoserver.controller.feign;

import cn.hutool.core.bean.BeanUtil;
import com.iuin.common.enums.ResponseCodeEnum;
import com.iuin.common.model.exceptions.BusinessException;
import com.iuin.common.utils.RespResult;
import com.iuin.ssoserver.api.feign.StudentFeign;
import com.iuin.ssoserver.api.model.req.StudentReq;
import com.iuin.ssoserver.api.model.resp.StudentResp;
import com.iuin.ssoserver.entity.StudentDO;
import com.iuin.ssoserver.service.IStudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 学生-controller
 *
 * @author fa
 */
@RestController
@RequiredArgsConstructor
public class StudentFeignController implements StudentFeign {

    private final IStudentService studentService;

    @Override
    public RespResult<StudentResp> studentInfo(@RequestBody @Valid StudentReq studentReq) {

        StudentDO studentDO = Optional.ofNullable(studentService.info(studentReq)).orElseThrow(() -> new BusinessException(ResponseCodeEnum.STUDENT_NOT_EXIST));

        return RespResult.success(BeanUtil.copyProperties(studentDO, StudentResp.class));
    }

}
