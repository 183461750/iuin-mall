package com.iuin.component.pluggable_annotation.compile_time_code_gen;

import com.iuin.component.pluggable_annotation.compile_time_code_gen.annotation.ClassSignatureConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试@ClassSignatureConstants注解功能的正确性
 */
public class TestClassSignatureConstants {

    /**
     * 测试基本功能 - 使用默认配置的常量类
     */
    @Test
    public void testBasicConstantsClass() {
        // 验证类名常量
        assertEquals("com.iuin.component.pluggable_annotation.compile_time_code_gen.TestClassSignatureConstants$BasicTestClass", 
                BasicTestClass.Signatures.CLASS_NAME);
        
        // 验证简单类名常量
        assertEquals("BasicTestClass", BasicTestClass.Signatures.SIMPLE_CLASS_NAME);
        
        // 验证方法签名常量
        assertEquals("void publicMethod()", BasicTestClass.Signatures.PUBLIC_METHOD);
        assertEquals("int calculateSum(int, int)", BasicTestClass.Signatures.CALCULATE_SUM);
    }

    /**
     * 测试枚举模式
     */
    @Test
    public void testEnumMode() {
        // 验证类名枚举值
        assertEquals("com.iuin.component.pluggable_annotation.compile_time_code_gen.TestClassSignatureConstants$EnumTestClass", 
                EnumTestClass.Signatures.CLASS_NAME.getValue());
        
        // 验证方法签名枚举值
        assertEquals("String getData()", EnumTestClass.Signatures.GET_DATA.getValue());
    }

    /**
     * 测试自定义前缀和后缀
     */
    @Test
    public void testCustomPrefixSuffix() {
        // 验证自定义前缀和后缀的常量名
        assertEquals("com.iuin.component.pluggable_annotation.compile_time_code_gen.TestClassSignatureConstants$CustomPrefixSuffixClass", 
                CustomPrefixSuffixClass.Signatures.PREFIX_CLASS_NAME_SUFFIX);
        
        assertEquals("void customMethod()", CustomPrefixSuffixClass.Signatures.PREFIX_CUSTOM_METHOD_SUFFIX);
    }

    /**
     * 测试只包含公共方法
     */
    @Test
    public void testOnlyPublicMethods() {
        // 验证只包含公共方法
        assertNotNull(OnlyPublicMethodsClass.Signatures.PUBLIC_METHOD);
        
        // 以下断言会失败，因为私有方法不应该生成常量
        try {
            // 尝试访问私有方法常量（应该不存在）
            OnlyPublicMethodsClass.Signatures.PRIVATE_METHOD;
            fail("Should not have generated constant for private method");
        } catch (NoSuchFieldError e) {
            // 预期的异常
        }
    }

    /**
     * 基本测试类 - 使用默认配置
     */
    @ClassSignatureConstants
    public static class BasicTestClass {
        public void publicMethod() {
            // 测试方法
        }
        
        public int calculateSum(int a, int b) {
            return a + b;
        }
        
        private void privateMethod() {
            // 私有方法
        }
    }

    /**
     * 枚举模式测试类
     */
    @ClassSignatureConstants(asEnum = true)
    public static class EnumTestClass {
        public String getData() {
            return "data";
        }
    }

    /**
     * 自定义前缀和后缀测试类
     */
    @ClassSignatureConstants(prefix = "PREFIX_", suffix = "_SUFFIX")
    public static class CustomPrefixSuffixClass {
        public void customMethod() {
            // 测试方法
        }
    }

    /**
     * 只包含公共方法测试类
     */
    @ClassSignatureConstants(onlyPublicMethods = true)
    public static class OnlyPublicMethodsClass {
        public void publicMethod() {
            // 公共方法
        }
        
        private void privateMethod() {
            // 私有方法 - 不应该生成常量
        }
        
        protected void protectedMethod() {
            // 保护方法 - 不应该生成常量
        }
    }
}
