package com.iuin.component.base.annos;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 安全调用注解
 * 用于标记方法，使其在AOP中被包装，捕获异常并记录日志，不影响主流程执行
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SafeCall {
    
    /**
     * 异常描述信息
     * @return 异常描述
     */
    String value() default "";
    
    /**
     * 是否记录异常堆栈信息
     * @return true-记录堆栈，false-只记录异常信息
     */
    boolean stackTrace() default false;
}