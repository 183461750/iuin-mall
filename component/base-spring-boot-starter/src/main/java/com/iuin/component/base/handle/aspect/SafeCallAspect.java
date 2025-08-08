package com.iuin.component.base.handle.aspect;

import com.iuin.component.base.annos.SafeCall;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 安全调用切面
 * 用于包装被@SafeCall注解标记的方法，捕获异常并记录日志，不影响主流程执行
 */
@Slf4j
@Aspect
@Order(2)
@Component
public class SafeCallAspect {

    /**
     * 定义切点，匹配所有被@SafeCall注解标记的方法
     */
    @Pointcut("@annotation(com.iuin.component.base.annos.SafeCall)")
    public void safeCallPointcut() {
    }

    /**
     * 环绕通知，处理被@SafeCall注解标记的方法
     *
     * @param joinPoint 连接点
     * @return 方法执行结果
     */
    @Around("safeCallPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        
        // 获取注解信息
        SafeCall safeCall = method.getAnnotation(SafeCall.class);
        String description = safeCall.value();
        boolean stackTrace = safeCall.stackTrace();
        
        // 获取类名和方法名
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = method.getName();
        
        try {
            // 执行原方法
            Object result = joinPoint.proceed();
            return result;
        } catch (Throwable throwable) {
            // 记录异常日志
            StringBuilder logMessage = new StringBuilder();
            logMessage.append("[SafeCall] 方法执行异常 - ");
            logMessage.append("类名: ").append(className).append(", ");
            logMessage.append("方法名: ").append(methodName);
            
            if (!description.isEmpty()) {
                logMessage.append(", 描述: ").append(description);
            }
            
            logMessage.append(", 异常信息: ").append(throwable.getMessage());
            
            if (stackTrace) {
                log.error(logMessage.toString(), throwable);
            } else {
                log.error(logMessage.toString());
            }
            
            // 返回null或者默认值，不中断程序执行
            return null;
        }
    }
}