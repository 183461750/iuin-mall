package com.iuin.component.test.mapstruct;

import cn.hutool.core.date.DatePattern;
import com.iuin.component.test.mapstruct.model.TestDO;
import com.iuin.component.test.mapstruct.model.TestVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author fa
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface Test2MS {

    Test2MS INSTANCE = Mappers.getMapper(Test2MS.class);


    @Mappings({
            @Mapping(target = TestDO.Fields.age, source = TestVO.Fields.userAge),
            @Mapping(target = "isDefault", expression = "java(cn.hutool.core.convert.Convert.toBool(dto.getIsDefault()))")
    })
    TestDO voToDo(TestVO dto);

    @Mapping(target = "userAge", source = "age")
    @Mapping(target = "userCreateTime", source = "createTime", dateFormat = DatePattern.NORM_DATETIME_PATTERN)
    @Mapping(target = "isDefault", expression = "java(cn.hutool.core.convert.Convert.toInt(po.getIsDefault()))")
    TestVO poToVo(TestDO po);

    List<TestVO> poListToVoList(List<TestDO> po);

}
