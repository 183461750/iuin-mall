package com.iuin.ssoserver.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import com.iuin.ssoserver.api.model.req.StudentReq;
import com.iuin.ssoserver.dto.StudentAddRequest;
import com.iuin.ssoserver.entity.AddressDO;
import com.iuin.ssoserver.entity.PetDO;
import com.iuin.ssoserver.entity.RoleDO;
import com.iuin.ssoserver.entity.StudentDO;
import com.iuin.ssoserver.repository.StudentRepository;
import com.iuin.ssoserver.service.IStudentService;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author fa
 */
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements IStudentService {

    private final StudentRepository studentRepository;
    // TODO @Fa 没有在jpa组件中自动注入这个类
//    private final JPAQueryFactory jpaQueryFactory;

//    private static final QStudentDO qStudentDO = QStudentDO.studentDO;


    @Override
    public StudentDO add(StudentAddRequest studentAddRequest) {
        StudentDO studentDO = new StudentDO();
        BeanUtil.copyProperties(studentAddRequest, studentDO);

        AddressDO addressDO = new AddressDO();
        BeanUtil.copyProperties(studentAddRequest, addressDO);

        RoleDO roleDO = new RoleDO();
        roleDO.setName(studentAddRequest.getRoleName());

        PetDO petDO = new PetDO();
        petDO.setAge(studentAddRequest.getPetAge());
        petDO.setSex(studentAddRequest.getPetSex());
        petDO.setName(studentAddRequest.getPetName());
        petDO.setStudentDO(studentDO);

        studentDO.setRoleDOList(ListUtil.toList(roleDO));
        studentDO.setPetDOList(ListUtil.toList(petDO));
        studentDO.setAddressDO(addressDO);

        return studentRepository.save(studentDO);
    }

    @Override
    public StudentDO info(StudentReq studentReq) {
//        JPAQuery<StudentDO> jpaQuery = jpaQueryFactory
//                .selectFrom(qStudentDO)
//                .where(qStudentDO.name.like("%" + studentReq.getName() + "%"));
//        return jpaQuery.fetchFirst();
        // TODO @Fa 动态生成Q版实体有问题
        StudentDO studentDO = new StudentDO();
        studentDO.setName(studentReq.getName());
        return studentDO;
    }

}
