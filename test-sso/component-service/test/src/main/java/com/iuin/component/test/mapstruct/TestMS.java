package com.iuin.component.test.mapstruct;

import cn.hutool.core.date.DatePattern;
import com.iuin.component.test.mapstruct.model.TestDO;
import com.iuin.component.test.mapstruct.model.TestVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * @author fa
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TestMS {

//    @Mappings({
//            @Mapping(target = "age", source = "userAge"),
//    })
//    TestDO VoToDo(TestVO dto);
//
//    @Mappings({
//            @Mapping(target = "userAge", source = "age"),
//            @Mapping(target = "userCreateTime", source = "createTime", dateFormat = DatePattern.NORM_DATETIME_PATTERN),
//    })
//    TestVO poToVo(TestDO po);
//
//    List<TestVO> poListToVoList(List<TestDO> po);

}
