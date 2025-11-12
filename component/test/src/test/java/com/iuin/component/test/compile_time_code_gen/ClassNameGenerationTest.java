package com.iuin.component.test.compile_time_code_gen;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClassNameGenerationTest {

    @Test
    public void testNormalClassName() {
        assertEquals("NormalClass", NormalClass_Signatures.SIMPLE_CLASS_NAME);
        assertTrue(NormalClass_Signatures.CLASS_NAME.endsWith("NormalClass"));
    }

    @Test
    public void testInnerClassName() {
        assertEquals("Outer$Inner", Inner_Signatures.SIMPLE_CLASS_NAME);
        assertTrue(Inner_Signatures.CLASS_NAME.endsWith("Outer$Inner"));
    }

    @Test
    public void testGenericClassName() {
        assertEquals("GenericClass", GenericClass_Signatures.SIMPLE_CLASS_NAME);
        assertTrue(GenericClass_Signatures.CLASS_NAME.endsWith("GenericClass"));
    }

    @Test
    public void testAnonymousClassDoesNotAffect() {
        Object anon = new Object() { };
        assertEquals("NormalClass", NormalClass_Signatures.SIMPLE_CLASS_NAME);
        assertNotNull(anon);
    }
}
