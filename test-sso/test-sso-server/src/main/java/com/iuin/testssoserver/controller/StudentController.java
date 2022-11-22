package com.iuin.testssoserver.controller;

import cn.dev33.satoken.util.SaResult;
import com.iuin.testssoserver.dto.StudentAddRequest;
import com.iuin.testssoserver.entity.StudentDO;
import com.iuin.testssoserver.service.IStudentService;
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

        studentDO.getPetDOList().forEach(petDO -> petDO.setStudentDO(null));

        return SaResult.data(studentDO);
    }

}
