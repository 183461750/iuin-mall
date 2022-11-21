package com.iuin.testssoserver.service;

import com.iuin.testssoserver.dto.StudentAddRequest;
import com.iuin.testssoserver.entity.StudentDO;

/**
 * @author fa
 */
public interface IStudentService {

    StudentDO add(StudentAddRequest studentAddRequest);

}
