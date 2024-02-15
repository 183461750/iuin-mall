package com.iuin.component.pluggable_annotation.take_time.agent.test2;

import org.objectweb.asm.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class MyClassFileTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className.equals("com.iuin.component.test.TakeTimeTest")) {
            ClassReader classReader = new ClassReader(classfileBuffer);
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            classReader.accept(new ClassVisitor(Opcodes.ASM5, classWriter) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                    if ("doSomething".equals(name)) {
                        // 返回一个自定义的MethodVisitor来修改方法
                        return new MethodVisitor(Opcodes.ASM5, super.visitMethod(access, name, desc, signature, exceptions)) {
                            @Override
                            public void visitCode() {
                                // 在这里添加时间记录的代码
                                // ...
                                System.out.println("你好👋visitCode");
                            }
                        };
                    }
                    // 返回默认的MethodVisitor来处理其他方法
                    return super.visitMethod(access, name, desc, signature, exceptions);
                }
            }, 0);
            return classWriter.toByteArray();
        }
        return classfileBuffer;
    }
}
