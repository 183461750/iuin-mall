package com.iuin.b.controller;

import cn.dev33.satoken.util.SaResult;
import com.iuin.common.utils.RespResult;
import com.iuin.ssoserver.api.feign.StudentFeign;
import com.iuin.ssoserver.api.model.req.StudentReq;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fa
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    private StudentFeign studentFeign;

    /**
     * 查询学生信息
     */
    @GetMapping("/info")
    public RespResult<?> studentInfo(StudentReq studentReq) {

        // 返回给前端
        return studentFeign.studentInfo(studentReq);
    }

    @RequestMapping("/demo")
    public Object testDemo(String str) {

        // 返回给前端
        return SaResult.data(str);
    }

}
