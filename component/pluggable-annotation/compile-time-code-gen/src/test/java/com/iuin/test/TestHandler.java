package com.iuin.test;

import com.iuin.component.pluggable_annotation.compile_time_code_gen.annotation.ClassMetaConstants;
import lombok.Data;

/**
 * 测试类，用于验证ClassMetaConstantsHandler
 */
@Data
@ClassMetaConstants(innerClassName = "Constants", prefix = "PREFIX_", suffix = "_SUFFIX")
public class TestHandler {
    private String name;
    private int age;

    public void testMethod(String param) {
        // 测试方法
    }
}