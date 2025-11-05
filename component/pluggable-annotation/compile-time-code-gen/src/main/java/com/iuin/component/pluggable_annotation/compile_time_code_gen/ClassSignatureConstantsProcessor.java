package com.iuin.component.pluggable_annotation.compile_time_code_gen;

import com.iuin.component.pluggable_annotation.compile_time_code_gen.annotation.ClassSignatureConstants;
import com.squareup.javapoet.*;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

// 使用AutoService自动注册注解处理器
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.iuin.component.pluggable_annotation.compile_time_code_gen.annotation.ClassSignatureConstants")
@AutoService(Processor.class)
public class ClassSignatureConstantsProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;
    private Elements elementUtils;
    private Types typeUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
        typeUtils = processingEnv.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(ClassSignatureConstants.class)) {
            if (element instanceof TypeElement) {
                TypeElement typeElement = (TypeElement) element;
                ClassSignatureConstants annotation = typeElement.getAnnotation(ClassSignatureConstants.class);
                
                try {
                    generateConstants(typeElement, annotation);
                } catch (IOException e) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "Failed to generate constants: " + e.getMessage());
                }
            }
        }
        return true;
    }

    /**
     * 生成常量代码
     */
    private void generateConstants(TypeElement typeElement, ClassSignatureConstants annotation) throws IOException {
        String className = typeElement.getSimpleName().toString();
        String packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
        String innerClassName = annotation.innerClassName();
        String prefix = annotation.prefix();
        String suffix = annotation.suffix();
        boolean asEnum = annotation.asEnum();
        boolean onlyPublicMethods = annotation.onlyPublicMethods();
        boolean includeInheritedMethods = annotation.includeInheritedMethods();

        // 创建内部类/枚举构建器
        TypeSpec.Builder innerTypeBuilder;
        if (asEnum) {
            innerTypeBuilder = createEnumBuilder(innerClassName, typeElement, prefix, suffix, onlyPublicMethods, includeInheritedMethods);
        } else {
            innerTypeBuilder = createConstantsClassBuilder(innerClassName, typeElement, prefix, suffix, onlyPublicMethods, includeInheritedMethods);
        }

        // 创建外部类（与原始类相同的类，但添加生成的内部类）
        TypeSpec outerType = TypeSpec.classBuilder(className)
                .addModifiers(typeElement.getModifiers().toArray(new Modifier[0]))
                .addType(innerTypeBuilder.build())
                .build();

        // 生成Java文件
        JavaFile javaFile = JavaFile.builder(packageName, outerType)
                .build();
        
        javaFile.writeTo(filer);
    }

    /**
     * 创建常量类构建器
     */
    private TypeSpec.Builder createConstantsClassBuilder(String innerClassName, TypeElement typeElement, 
                                                        String prefix, String suffix, 
                                                        boolean onlyPublicMethods, boolean includeInheritedMethods) {
        TypeSpec.Builder builder = TypeSpec.classBuilder(innerClassName)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);

        // 添加类名常量
        String classNameConstant = prefix + "CLASS_NAME" + suffix;
        builder.addField(FieldSpec.builder(String.class, classNameConstant)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", typeElement.getQualifiedName().toString())
                .build());

        // 添加简单类名常量
        String simpleClassNameConstant = prefix + "SIMPLE_CLASS_NAME" + suffix;
        builder.addField(FieldSpec.builder(String.class, simpleClassNameConstant)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", typeElement.getSimpleName().toString())
                .build());

        // 获取并添加方法签名常量
        Set<ExecutableElement> methods = getMethods(typeElement, onlyPublicMethods, includeInheritedMethods);
        for (ExecutableElement method : methods) {
            String methodName = method.getSimpleName().toString();
            String methodSignature = generateMethodSignature(method);
            String constantName = prefix + toConstantName(methodName) + suffix;
            
            builder.addField(FieldSpec.builder(String.class, constantName)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .initializer("$S", methodSignature)
                    .build());
        }

        return builder;
    }

    /**
     * 创建枚举构建器
     */
    private TypeSpec.Builder createEnumBuilder(String innerClassName, TypeElement typeElement, 
                                             String prefix, String suffix, 
                                             boolean onlyPublicMethods, boolean includeInheritedMethods) {
        // 创建枚举值构建器列表
        List<TypeSpec> enumConstants = new ArrayList<>();
        
        // 添加类名枚举值
        String classNameEnumName = toEnumName(prefix + "CLASS_NAME" + suffix);
        TypeSpec classNameEnum = TypeSpec.enumBuilder(classNameEnumName)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addEnumConstant("VALUE", TypeSpec.anonymousClassBuilder("$S", typeElement.getQualifiedName().toString()).build())
                .build();
        enumConstants.add(classNameEnum);

        // 添加简单类名枚举值
        String simpleClassNameEnumName = toEnumName(prefix + "SIMPLE_CLASS_NAME" + suffix);
        TypeSpec simpleClassNameEnum = TypeSpec.enumBuilder(simpleClassNameEnumName)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addEnumConstant("VALUE", TypeSpec.anonymousClassBuilder("$S", typeElement.getSimpleName().toString()).build())
                .build();
        enumConstants.add(simpleClassNameEnum);

        // 创建枚举类构建器
        TypeSpec.Builder enumBuilder = TypeSpec.enumBuilder(innerClassName)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addField(FieldSpec.builder(String.class, "value")
                        .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                        .build())
                .addMethod(MethodSpec.constructorBuilder()
                        .addParameter(String.class, "value")
                        .addStatement("this.value = value")
                        .build())
                .addMethod(MethodSpec.methodBuilder("getValue")
                        .addModifiers(Modifier.PUBLIC)
                        .returns(String.class)
                        .addStatement("return value")
                        .build());

        // 添加方法签名枚举常量
        Set<ExecutableElement> methods = getMethods(typeElement, onlyPublicMethods, includeInheritedMethods);
        for (ExecutableElement method : methods) {
            String methodName = method.getSimpleName().toString();
            String methodSignature = generateMethodSignature(method);
            String enumName = toEnumName(prefix + toConstantName(methodName) + suffix);
            
            enumBuilder.addEnumConstant(enumName, TypeSpec.anonymousClassBuilder("$S", methodSignature).build());
        }

        return enumBuilder;
    }

    /**
     * 获取类的方法列表
     */
    private Set<ExecutableElement> getMethods(TypeElement typeElement, boolean onlyPublicMethods, boolean includeInheritedMethods) {
        Set<ExecutableElement> methods = new HashSet<>();
        
        // 获取当前类声明的方法
        for (Element enclosed : typeElement.getEnclosedElements()) {
            if (enclosed instanceof ExecutableElement && enclosed.getKind() == ElementKind.METHOD) {
                ExecutableElement method = (ExecutableElement) enclosed;
                // 过滤构造方法
                if (method.getSimpleName().contentEquals("<init>") || method.getSimpleName().contentEquals("<clinit>")) {
                    continue;
                }
                
                // 根据可见性过滤
                if (!onlyPublicMethods || method.getModifiers().contains(Modifier.PUBLIC)) {
                    methods.add(method);
                }
            }
        }
        
        // 如果需要包含继承的方法
        if (includeInheritedMethods) {
            // 这里可以通过typeUtils实现更复杂的继承方法获取逻辑
            // 暂时简化实现
        }
        
        return methods;
    }

    /**
     * 生成方法签名
     */
    private String generateMethodSignature(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();
        String returnType = method.getReturnType().toString();
        
        // 构建参数类型字符串
        String params = method.getParameters().stream()
                .map(p -> p.asType().toString())
                .collect(Collectors.joining(", "));
        
        return returnType + " " + methodName + "(" + params + ")";
    }

    /**
     * 将方法名转换为常量名格式
     */
    private String toConstantName(String name) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (i > 0 && Character.isUpperCase(c) && !Character.isUpperCase(name.charAt(i-1))) {
                result.append('_');
            }
            result.append(Character.toUpperCase(c));
        }
        return result.toString();
    }

    /**
     * 将常量名转换为枚举名格式（移除非法字符）
     */
    private String toEnumName(String name) {
        // 移除非法的枚举名称字符
        return name.replaceAll("[^a-zA-Z0-9_]", "_");
    }
}
