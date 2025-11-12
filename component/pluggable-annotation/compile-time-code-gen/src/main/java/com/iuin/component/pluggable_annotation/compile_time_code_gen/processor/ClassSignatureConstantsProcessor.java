package com.iuin.component.pluggable_annotation.compile_time_code_gen.processor;

import com.google.auto.service.AutoService;
import com.iuin.component.pluggable_annotation.compile_time_code_gen.annotation.ClassSignatureConstants;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;

/**
 * 标准Java注解处理器，用于处理@ClassSignatureConstants注解
 * 生成类签名常量
 */
@AutoService(javax.annotation.processing.Processor.class)
public class ClassSignatureConstantsProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) {
            return false;
        }

        for (Element element : roundEnv.getElementsAnnotatedWith(ClassSignatureConstants.class)) {
            try {
                if (element.getKind().isClass()) {
                    TypeElement typeElement = (TypeElement) element;
                    generateClassConstants(typeElement);
                }
            } catch (Exception e) {
                processingEnv.getMessager().printMessage(
                    Diagnostic.Kind.ERROR,
                    "处理ClassSignatureConstants注解失败: " + e.getMessage()
                );
            }
        }
        return true;
    }

    private void generateClassConstants(TypeElement typeElement) throws IOException {
        String className = typeElement.getSimpleName().toString();
        String packageName = processingEnv.getElementUtils().getPackageOf(typeElement).toString();
        String classSignature = typeElement.getQualifiedName().toString();
        
        // 生成常量名，将类名转换为大写并用下划线分隔
        String constantName = convertToConstantName(className);
        
        // 创建常量字段
        FieldSpec fieldSpec = FieldSpec.builder(
                String.class,
                constantName,
                Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL
            )
            .initializer("$S", classSignature)
            .build();
        
        // 创建内部类Clazz
        TypeSpec clazzInnerClass = TypeSpec.classBuilder("Clazz")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
            .addField(fieldSpec)
            .build();
        
        // 创建Java文件
        JavaFile javaFile = JavaFile.builder(packageName, clazzInnerClass)
            .build();
        
        // 写入文件
        javaFile.writeTo(processingEnv.getFiler());
        
        processingEnv.getMessager().printMessage(
            Diagnostic.Kind.NOTE,
            "已生成类签名常量: " + packageName + ".Clazz." + constantName
        );
    }
    
    private String convertToConstantName(String className) {
        // 将驼峰命名转换为大写下划线命名
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < className.length(); i++) {
            char c = className.charAt(i);
            if (i > 0 && Character.isUpperCase(c)) {
                sb.append('_');
            }
            sb.append(Character.toUpperCase(c));
        }
        return sb.toString();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(ClassSignatureConstants.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
