package com.iuin.component.test.mapstruct;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.iuin.component.test.mapstruct.model.TestDO;
import com.iuin.component.test.mapstruct.model.TestVO;

import java.time.LocalDateTime;

/**
 * @author fa
 */
public class TestMain2 {

    public static void main(String[] args) {
//        TestDO testDO = Test2MS.INSTANCE.voToDo(new TestVO("Hello", 18, DateUtil.date(LocalDateTime.now()).toString(DatePattern.NORM_DATETIME_PATTERN), 1));

//        System.out.println(JSONUtil.toJsonPrettyStr(testDO));

        System.out.println("----------------------------------------------");

//        TestVO testVO = Test2MS.INSTANCE.poToVo(new TestDO("Hello", 18, LocalDateTime.now(), true));

//        System.out.println(JSONUtil.toJsonPrettyStr(testVO));

    }

}
