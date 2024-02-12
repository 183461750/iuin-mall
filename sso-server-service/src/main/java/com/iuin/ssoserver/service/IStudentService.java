package com.iuin.ssoserver.service;

import com.iuin.ssoserver.api.model.req.StudentReq;
import com.iuin.ssoserver.dto.StudentAddRequest;
import com.iuin.ssoserver.entity.StudentDO;

/**
 * @author fa
 */
public interface IStudentService {

    StudentDO add(StudentAddRequest studentAddRequest);

    /**
     * 查询学生信息
     */
    StudentDO info(StudentReq studentReq);

}
