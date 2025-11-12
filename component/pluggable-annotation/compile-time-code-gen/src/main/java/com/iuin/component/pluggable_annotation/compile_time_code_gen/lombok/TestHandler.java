package com.iuin.component.pluggable_annotation.compile_time_code_gen.lombok;

import com.iuin.component.pluggable_annotation.compile_time_code_gen.annotation.ClassSignatureConstants;
import lombok.Data;

/**
 * 测试类，用于验证@ClassSignatureConstants注解和处理器
 */
@Data
@ClassSignatureConstants(innerClassName = "TestConstants")
public class TestHandler {
    private String name;
    private int age;
    
    public void testMethod() {
        System.out.println("Test method");
    }
    
    public String getNameWithPrefix(String prefix) {
        return prefix + this.name;
    }
}