package com.iuin.component.pluggable_annotation.compile_time_code_gen;

import com.iuin.component.pluggable_annotation.compile_time_code_gen.annotation.ClassSignatureConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试复杂场景下@ClassSignatureConstants注解的功能
 */
public class TestComplexScenarios {

    /**
     * 测试继承关系
     */
    @Test
    public void testInheritance() {
        // 验证子类自己的方法
        assertEquals("void childMethod()", ChildClass.Signatures.CHILD_METHOD);
        
        // 验证父类方法（当includeInheritedMethods=true时）
        assertEquals("void parentMethod()", ChildClass.Signatures.PARENT_METHOD);
    }

    /**
     * 测试不同访问修饰符
     */
    @Test
    public void testDifferentAccessModifiers() {
        // 验证公共方法
        assertEquals("void publicMethod()", AccessModifiersClass.Signatures.PUBLIC_METHOD);
        
        // 验证保护方法
        assertEquals("void protectedMethod()", AccessModifiersClass.Signatures.PROTECTED_METHOD);
        
        // 验证默认（包私有）方法
        assertEquals("void defaultMethod()", AccessModifiersClass.Signatures.DEFAULT_METHOD);
        
        // 验证私有方法
        assertEquals("void privateMethod()", AccessModifiersClass.Signatures.PRIVATE_METHOD);
    }

    /**
     * 测试自定义内部类名
     */
    @Test
    public void testCustomInnerClassName() {
        // 验证使用自定义内部类名
        assertEquals("com.iuin.component.pluggable_annotation.compile_time_code_gen.TestComplexScenarios$CustomInnerNameClass", 
                CustomInnerNameClass.MyConstants.CLASS_NAME);
    }

    /**
     * 测试泛型方法
     */
    @Test
    public void testGenericMethods() {
        // 验证泛型方法签名
        assertEquals("<T> T genericMethod(T)", GenericClass.Signatures.GENERIC_METHOD);
        assertEquals("<K,V> V getValue(Map<K,V>, K)", GenericClass.Signatures.GET_VALUE);
    }

    /**
     * 测试带参数的方法
     */
    @Test
    public void testMethodsWithParameters() {
        // 验证带参数的方法签名
        assertEquals("void methodWithParams(int, String, boolean)", ParamMethodsClass.Signatures.METHOD_WITH_PARAMS);
        assertEquals("String concatStrings(String...)", ParamMethodsClass.Signatures.CONCAT_STRINGS);
    }

    /**
     * 父类
     */
    public static class ParentClass {
        public void parentMethod() {
            // 父类方法
        }
    }

    /**
     * 子类 - 包含继承方法
     */
    @ClassSignatureConstants(includeInheritedMethods = true)
    public static class ChildClass extends ParentClass {
        public void childMethod() {
            // 子类方法
        }
    }

    /**
     * 不同访问修饰符测试类
     */
    @ClassSignatureConstants
    public static class AccessModifiersClass {
        public void publicMethod() {}
        protected void protectedMethod() {}
        void defaultMethod() {}
        private void privateMethod() {}
    }

    /**
     * 自定义内部类名测试类
     */
    @ClassSignatureConstants(innerClassName = "MyConstants")
    public static class CustomInnerNameClass {
        public void testMethod() {}
    }

    /**
     * 泛型测试类
     */
    @ClassSignatureConstants
    public static class GenericClass {
        public <T> T genericMethod(T input) {
            return input;
        }
        
        public <K, V> V getValue(java.util.Map<K, V> map, K key) {
            return map.get(key);
        }
    }

    /**
     * 带参数的方法测试类
     */
    @ClassSignatureConstants
    public static class ParamMethodsClass {
        public void methodWithParams(int a, String b, boolean c) {}
        
        public String concatStrings(String... strings) {
            return String.join("", strings);
        }
    }
}
