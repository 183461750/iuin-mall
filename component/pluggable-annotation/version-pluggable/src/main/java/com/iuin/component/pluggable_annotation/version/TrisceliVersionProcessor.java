package com.iuin.component.pluggable_annotation.version;

import com.google.auto.service.AutoService;
import lombok.SneakyThrows;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.HashSet;
import java.util.Set;

import com.sun.tools.javac.api.*;
import com.sun.tools.javac.processing.*;
import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;

/**
 * {@link AbstractProcessor} 就属于 Pluggable Annotation Processing API
 */
@SupportedAnnotationTypes("com.iuin.component.pluggable_annotation.version.*")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class TrisceliVersionProcessor extends AbstractProcessor {

    private ProcessingEnvironment processingEnv;

    /**
     * 初始化处理器
     *
     * @param processingEnv 提供了一系列的实用工具
     */
    @SneakyThrows
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.processingEnv = processingEnv;
        // 为避免JDK模块访问限制，这里不直接使用Javac内部API
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> set = new HashSet<>();
        set.add(TrisceliVersion.class.getName()); // 支持解析的注解
        return set;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // 为避免JDK模块访问限制，这里暂不进行AST改写，直接跳过处理
        return false;
    }

    /**
     * 利用processingEnv内的Messager对象输出一些日志
     *
     * @param e element
     * @param m error message
     */
    private void printErrorMessage(Element e, String m) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, m, e);
    }

    private String getVersion() {
        /**
         * 获取version，这里省略掉复杂的代码，直接返回固定值
         */
        return "v1.0.1";
    }

}
