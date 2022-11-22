package com.iuin.testssoserver.serviceimpl;

import java.time.LocalDateTime;

import cn.hutool.core.collection.ListUtil;

import cn.hutool.core.bean.BeanUtil;
import com.iuin.testssoserver.dto.StudentAddRequest;
import com.iuin.testssoserver.entity.AddressDO;
import com.iuin.testssoserver.entity.PetDO;
import com.iuin.testssoserver.entity.RoleDO;
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

}
