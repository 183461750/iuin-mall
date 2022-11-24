package com.iuin.ssoserver.controller;

import cn.dev33.satoken.util.SaResult;
import com.iuin.ssoserver.dto.StudentAddRequest;
import com.iuin.ssoserver.entity.StudentDO;
import com.iuin.ssoserver.service.IStudentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author fa
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    private IStudentService studentService;

    @PostMapping(value = "/add")
    public SaResult add(StudentAddRequest studentAddRequest) {

        StudentDO studentDO = studentService.add(studentAddRequest);

        return SaResult.data(studentDO);
    }

}
