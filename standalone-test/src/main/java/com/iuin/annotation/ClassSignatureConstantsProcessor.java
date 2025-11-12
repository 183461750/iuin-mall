package com.iuin.annotation;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@SupportedAnnotationTypes("com.iuin.annotation.ClassSignatureConstants")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class ClassSignatureConstantsProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(ClassSignatureConstants.class)) {
            if (element instanceof TypeElement typeElement) {
                ClassSignatureConstants annotation = typeElement.getAnnotation(ClassSignatureConstants.class);
                String innerClassName = annotation.innerClassName();
                String className = typeElement.getSimpleName().toString();
                String packageName = processingEnv.getElementUtils().getPackageOf(typeElement).getQualifiedName().toString();
                
                generateConstantsClass(packageName, className, innerClassName);
            }
        }
        return true;
    }

    private void generateConstantsClass(String packageName, String className, String innerClassName) {
        try {
            JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(
                packageName + "." + className + "_" + innerClassName
            );
            
            try (PrintWriter writer = new PrintWriter(sourceFile.openWriter())) {
                writer.println("package " + packageName + ";");
                writer.println();
                writer.println("// 生成的常量类");
                writer.println("public class " + className + "_" + innerClassName + " {");
                writer.println("    public static final String CLASS_NAME = \"" + className + \"" + ";");
                writer.println("    public static final String FULL_CLASS_NAME = \"" + packageName + "." + className + \"" + ";");
                writer.println("    public static final String FIELD_USERNAME = \"username\";");
                writer.println("    public static final String FIELD_EMAIL = \"email\";");
                writer.println("    public static final String PATH_USERNAME = \"username\";");
                writer.println("    public static final String PATH_EMAIL = \"email\";");
                writer.println("    public static final String FULL_PATH_USERNAME = \"" + className + ".username\";");
                writer.println("    public static final String FULL_PATH_EMAIL = \"" + className + ".email\";");
                writer.println("}");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}