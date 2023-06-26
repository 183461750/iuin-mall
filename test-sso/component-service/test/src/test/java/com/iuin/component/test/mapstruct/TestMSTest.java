package com.iuin.component.test.mapstruct;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.iuin.component.test.mapstruct.model.TestDO;
import com.iuin.component.test.mapstruct.model.TestVO;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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
        TestDO testDO = testMS.VoToDo(new TestVO("Hello", 18, DateUtil.date(LocalDateTime.now()).toString(DatePattern.NORM_DATETIME_PATTERN)));

        System.out.println(JSONUtil.toJsonPrettyStr(testDO));
    }

    @Test
    void poToVo() {
    }

    @Test
    void poListToVoList() {
    }
}