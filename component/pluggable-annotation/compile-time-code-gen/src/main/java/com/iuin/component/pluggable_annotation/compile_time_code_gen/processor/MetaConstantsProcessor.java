package com.iuin.component.pluggable_annotation.compile_time_code_gen.processor;

import com.google.auto.service.AutoService;
import com.iuin.component.pluggable_annotation.compile_time_code_gen.annotation.ClassMetaConstants;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.util.Set;

@AutoService(Processor.class)
public class MetaConstantsProcessor extends AbstractProcessor {
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(ClassMetaConstants.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty())
            return false;
        Elements elements = processingEnv.getElementUtils();
        for (Element e : roundEnv.getElementsAnnotatedWith(ClassMetaConstants.class)) {
            if (!(e instanceof TypeElement))
                continue;
            TypeElement type = (TypeElement) e;
            String pkg = elements.getPackageOf(type).getQualifiedName().toString();
            String binary = elements.getBinaryName(type).toString();
            String simple = stripPackage(binary);

            ClassMetaConstants cfg = e.getAnnotation(ClassMetaConstants.class);
            String innerName = cfg != null && !cfg.innerClassName().isBlank() ? cfg.innerClassName() : "Metas";
            String generatedName = type.getSimpleName().toString() + "_" + innerName;

            TypeSpec.Builder cls = TypeSpec.classBuilder(generatedName)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

            FieldSpec fSimple = FieldSpec
                    .builder(String.class, "SIMPLE_CLASS_NAME", Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .initializer("$S", simple)
                    .build();
            FieldSpec fFull = FieldSpec
                    .builder(String.class, "CLASS_NAME", Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .initializer("$S", binary)
                    .build();
            cls.addField(fSimple);
            cls.addField(fFull);

            JavaFile jf = JavaFile.builder(pkg, cls.build()).build();
            try {
                jf.writeTo(processingEnv.getFiler());
            } catch (IOException ex) {
                // ignore
            }
        }
        return true;
    }

    private String stripPackage(String binary) {
        int idx = binary.lastIndexOf('.') + 1;
        return idx > 0 ? binary.substring(idx) : binary;
    }
}