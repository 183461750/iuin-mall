package com.iuin.component.pluggable_annotation.compile_time_code_gen.lombok;

import com.iuin.component.pluggable_annotation.compile_time_code_gen.annotation.ClassSignatureConstants;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.HandlerLibrary;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import javax.tools.Diagnostic;
import java.util.HashMap;
import java.util.Map;

/**
 * Lombok处理器库，用于注册自定义注解处理器
 * 实现了Lombok的HandlerLibrary接口，并使用@ProviderFor注解将其注册为服务提供者
 */
@javax.annotation.Processing.API(value = javax.annotation.Processing.API.Status.STABLE)
public class ClassSignatureConstantsHandlerLibrary extends HandlerLibrary {

    public ClassSignatureConstantsHandlerLibrary() {
        super(SourceVersion.RELEASE_8);
    }

    @Override
    public Map<String, JavacAnnotationHandler<?>> getAnnotationHandlers(ProcessingEnvironment processingEnvironment) {
        Map<String, JavacAnnotationHandler<?>> handlers = new HashMap<>();
        
        try {
            // 注册ClassSignatureConstants注解处理器
            handlers.put(ClassSignatureConstants.class.getName(), new ClassSignatureConstantsHandler());
            
            // 记录注册信息
            processingEnvironment.getMessager().printMessage(
                Diagnostic.Kind.NOTE, 
                "已注册ClassSignatureConstants处理器"
            );
        } catch (Exception e) {
            processingEnvironment.getMessager().printMessage(
                Diagnostic.Kind.ERROR, 
                "注册ClassSignatureConstants处理器失败: " + e.getMessage()
            );
        }
        
        return handlers;
    }

    @Override
    public String getHandlerPackage() {
        return "com.iuin.component.pluggable_annotation.compile_time_code_gen.lombok";
    }
}
