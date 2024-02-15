package com.iuin.component.pluggable_annotation.take_time;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes("com.iuin.component.pluggable_annotation.take_time.*")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class TakeTimeProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;
    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        System.out.println("你好👋init");

        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("你好👋process");

        for (Element element : roundEnv.getElementsAnnotatedWith(TakeTime.class)) {
            if (element instanceof ExecutableElement) {
                ExecutableElement methodElement = (ExecutableElement) element;
                TypeElement enclosingElement = (TypeElement) methodElement.getEnclosingElement();
                PackageElement packageElement = elementUtils.getPackageOf(enclosingElement);

                String className = enclosingElement.getSimpleName().toString();
                String methodName = methodElement.getSimpleName().toString();
                String packageName = packageElement.isUnnamed() ? "" : packageElement.getQualifiedName().toString();

                MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder(methodName)
                        .addModifiers(Modifier.PUBLIC)
                        // 移除Override注解，因为我们不是重写父类方法
                        // .addAnnotation(Override.class)
                        .returns(void.class);

                // 添加参数
                for (VariableElement parameter : methodElement.getParameters()) {
                    // 从TypeMirror创建TypeName
                    TypeName parameterType = TypeName.get(parameter.asType());
                    // 创建ParameterSpec对象
                    ParameterSpec parameterSpec = ParameterSpec.builder(parameterType, parameter.getSimpleName().toString())
                            .build();
                    methodSpecBuilder.addParameter(parameterSpec);
                }

                // 添加异常
                for (TypeMirror thrownType : methodElement.getThrownTypes()) {
                    TypeName exceptionType = TypeName.get(thrownType);
                    methodSpecBuilder.addException(exceptionType);
                }

// 创建一个CodeBlock来表示原始方法体的代码
                CodeBlock originalMethodBody = CodeBlock.of("super.$L($L)", methodName, methodElement.getParameters());
// 添加时间记录代码
                CodeBlock timeLoggingCode = CodeBlock.builder()
                        .addStatement("$T start = System.currentTimeMillis()", long.class)
                        .addStatement(originalMethodBody)
                        .addStatement("$T end = System.currentTimeMillis()", long.class)
                        .addStatement("System.out.println(\"$L took \" + (end - start) + \"ms\")", methodName)
                        .build();
// 将原始方法体的代码和时间记录代码组合在一起
                CodeBlock combinedCode = CodeBlock.join(List.of(timeLoggingCode), "\n");
// 添加组合后的代码到新方法
                methodSpecBuilder.addCode(combinedCode);

                // 生成新的方法
                MethodSpec methodSpec = methodSpecBuilder
                        .addAnnotation(Override.class)
//                        .addStatement("$T start = System.currentTimeMillis()", long.class)
//                        .addCode("\n")
//                        .addStatement("super.$L($L)", methodName, methodElement.getParameters())
//                        .addCode("\n")
//                        .addStatement("$T end = System.currentTimeMillis()", long.class)
//                        .addStatement("System.out.println(\"$L took \" + (end - start) + \"ms\")", methodName)
                        .build();

//                TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className + "TakeTime");


                // 创建继承自原始类父类的类
//                TypeName superClass = TypeName.get(enclosingElement.getSuperclass());
                TypeName superClass = TypeName.get(enclosingElement.asType());
                TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className + "TakeTime")
                        .superclass(superClass) // 继承自原始类的父类
                        .addModifiers(Modifier.PUBLIC);

//                // 添加原始类的成员变量
//                for (Element enclosedElement : enclosingElement.getEnclosedElements()) {
//                    if (enclosedElement instanceof VariableElement && !enclosedElement.equals(methodElement)) { // 确保是字段并且不包含原始方法本身
//                        VariableElement fieldElement = (VariableElement) enclosedElement;
//                        TypeName fieldType = TypeName.get(fieldElement.asType());
//                        classBuilder.addField(FieldSpec.builder(fieldType, fieldElement.getSimpleName().toString())
//                                .addModifiers(Modifier.PRIVATE)
//                                .build());
//                    }
//                }

                TypeSpec typeSpec = classBuilder
//                        .addSuperinterface(TypeMirror.class) // 假设你的类实现了一个接口
                        .addMethod(methodSpec)
                        .build();

                JavaFile javaFile = JavaFile.builder(packageName, typeSpec)
                        .build();

                try {

                    javaFile.writeTo(filer);

//                    System.out.println("你好👋process-javaFile");
//                    // 获取生成的Java代码字符串
//                    String fileContent = javaFile.toString();
//                    // 逐行打印到控制台
//                    for (String line : fileContent.split("\n")) {
//                        System.out.println(line);
//                    }

                } catch (IOException e) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "Failed to write generated file: " + e.getMessage());
                }
            }
        }
        return true;
    }
}
