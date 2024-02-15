package com.iuin.component.pluggable_annotation.take_time.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class TimeTrackingTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if ("com.iuin.component.test.TakeTimeTest".equals(className)) {
            // 这里可以添加代码来修改字节码
            System.out.println("你好👋transform");

            // 例如，添加时间记录逻辑
            return classfileBuffer;
        }
        return classfileBuffer;
    }
}
