package com.iuin.testssoserver.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
import com.iuin.testssoserver.dto.StudentAddRequest;
import com.iuin.testssoserver.entity.StudentDO;
import com.iuin.testssoserver.repository.StudentRepository;
import com.iuin.testssoserver.service.IStudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author fa
 */
@Service
public class StudentServiceImpl implements IStudentService {

    @Resource
    private StudentRepository studentRepository;

    @Override
    public StudentDO add(StudentAddRequest studentAddRequest) {
        StudentDO studentDO = new StudentDO();
        BeanUtil.copyProperties(studentAddRequest, studentDO);
        return studentRepository.save(studentDO);
    }

}
