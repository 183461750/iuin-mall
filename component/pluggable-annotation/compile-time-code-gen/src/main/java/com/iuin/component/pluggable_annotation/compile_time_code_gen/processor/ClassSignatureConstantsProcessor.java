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
import javax.lang.model.util.Elements;
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
        Elements elements = processingEnv.getElementUtils();
        String packageName = elements.getPackageOf(typeElement).getQualifiedName().toString();
        String binaryFull = elements.getBinaryName(typeElement).toString();
        String simpleBinary = stripPackage(binaryFull);

        ClassSignatureConstants ann = typeElement.getAnnotation(ClassSignatureConstants.class);
        boolean asEnum = ann != null && ann.asEnum();
        String innerName = ann != null && !ann.innerClassName().isBlank() ? ann.innerClassName() : "Signatures";
        String generatedTypeName = typeElement.getSimpleName().toString() + "_" + innerName;
        String prefix = ann != null ? ann.prefix() : "";
        String suffix = ann != null ? ann.suffix() : "";

        String fieldSimple = buildConstName(prefix, "SIMPLE_CLASS_NAME", suffix);
        String fieldFull = buildConstName(prefix, "CLASS_NAME", suffix);

        TypeSpec.Builder typeBuilder;
        if (asEnum) {
            typeBuilder = TypeSpec.enumBuilder(generatedTypeName)
                .addModifiers(Modifier.PUBLIC);
        } else {
            typeBuilder = TypeSpec.classBuilder(generatedTypeName)
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.FINAL);
        }

        if (!asEnum) {
            FieldSpec simpleConst = FieldSpec.builder(String.class, fieldSimple, Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", simpleBinary)
                .build();
            FieldSpec fullConst = FieldSpec.builder(String.class, fieldFull, Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", binaryFull)
                .build();
            typeBuilder.addField(simpleConst);
            typeBuilder.addField(fullConst);
        } else {
            // 枚举场景：仅声明枚举常量名；值由名称表达（保持与 Lombok 风格一致）
            // 这里仍提供两个枚举常量名，用户可通过 name() 或自定义方法扩展获取字符串
            typeBuilder.addEnumConstant(fieldSimple);
            typeBuilder.addEnumConstant(fieldFull);
        }

        JavaFile javaFile = JavaFile.builder(packageName, typeBuilder.build()).build();
        javaFile.writeTo(processingEnv.getFiler());
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
            "已生成类名常量: " + packageName + "." + generatedTypeName + ".(" + fieldSimple + ", " + fieldFull + ")");
    }
    
    private String stripPackage(String binaryFull) {
        int idx = binaryFull.lastIndexOf('.');
        return idx >= 0 ? binaryFull.substring(idx + 1) : binaryFull;
    }

    private String buildConstName(String prefix, String base, String suffix) {
        String p = prefix == null ? "" : prefix;
        String s = suffix == null ? "" : suffix;
        return (p + base + s).replaceAll("[^A-Za-z0-9_$]", "_");
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
