package com.iuin.component.test.mapstruct;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestMSTest {

    @Resource
    private TestMS testMS;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void voToDo() {
//        TestDO testDO = testMS.voToDo(new TestVO("Hello", 18, DateUtil.date(LocalDateTime.now()).toString(DatePattern.NORM_DATETIME_PATTERN)));
//
//        System.out.println(JSONUtil.toJsonPrettyStr(testDO));
    }

    @Test
    void poToVo() {
//        TestVO testVO = testMS.poToVo(new TestDO("xxx", 18, LocalDateTime.now()));
//
//        System.out.println("冲：" + testVO);
    }

    @Test
    void poListToVoList() {
    }
}