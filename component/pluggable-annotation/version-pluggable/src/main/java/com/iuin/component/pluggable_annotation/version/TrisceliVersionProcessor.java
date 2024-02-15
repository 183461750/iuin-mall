package com.iuin.component.pluggable_annotation.version;

import lombok.SneakyThrows;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link AbstractProcessor} 就属于 Pluggable Annotation Processing API
 */
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
        return true;
    }

    private String getVersion() {
        /**
         * 获取version，这里省略掉复杂的代码，直接返回固定值
         */
        // TODO @Fa 这里到时候改成使用spring 自动注入获取配置文件中的版本号的方式
        return "v1.0.1";
    }
}