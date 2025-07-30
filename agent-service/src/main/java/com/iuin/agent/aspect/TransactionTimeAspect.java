package com.iuin.agent.aspect;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 事务时间打印切面
 *
 * @author fa
 */
@Aspect
@Component
@Slf4j
public class TransactionTimeAspect {

    private final Map<String, MethodInfo> methodInfoMap = new HashMap<>();

    /**
     * 配置切入规则
     */
    @Pointcut("execution(* *(..)) && @annotation(org.springframework.transaction.annotation.Transactional)")
    public void transactionTimeAspect() {
    }

    @Before("transactionTimeAspect()")
    public void beforeMethod(JoinPoint joinPoint) {
        long startTime = System.currentTimeMillis();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        MethodInfo methodInfo = new MethodInfo(startTime, getLocation(className, methodName), getCallerLocation());
        String key = getKeyByThreadAndLocation(className, methodName);
        methodInfoMap.put(key, methodInfo);
    }

    @After("transactionTimeAspect()")
    public void afterMethod(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        // 使用 get 方法获取值
        String key = getKeyByThreadAndLocation(className, methodName);
        try {
            MethodInfo methodInfo = methodInfoMap.get(key);
            if (methodInfo == null) {
                return;
            }
            long endTime = System.currentTimeMillis();
            long duration = endTime - methodInfo.startTime();

            // 打印事务执行时间及方法信息，包括行号
            Map<String, Integer> threadTransCountMap = getThreadTransCountMap();
            // PS: 代码行为 -1 时, 则代表可能是框架内部调用的, 就只需要看 [事务方法代码位置] 就行
            log.info("[事务时间统计]: 事务持续时间: {} ms, 线程ID: {}, 事务方法代码位置: {}, 调用者方法代码位置: {}, 线程-事务计数: {}",
                    duration, Thread.currentThread().getId(), methodInfo.transLocation(), methodInfo.callerLocation(), threadTransCountMap);
        } catch (Exception e) {
            log.error("[事务时间统计]: afterMethod异常", e);
        } finally {
            // 确保在任何情况下都移除 ThreadLocal 的值
            methodInfoMap.remove(key);
        }
    }

    @AfterThrowing(pointcut = "transactionTimeAspect()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        // 使用 get 方法获取值
        String key = getKeyByThreadAndLocation(className, methodName);
        try {
            MethodInfo methodInfo = methodInfoMap.get(key);
            if (methodInfo == null) {
                return;
            }
            long endTime = System.currentTimeMillis();
            long duration = endTime - methodInfo.startTime();

            // 打印事务执行时间、方法信息和异常信息，包括行号
            Map<String, Integer> threadTransCountMap = getThreadTransCountMap();
            // PS: 代码行为 -1 时, 则代表可能是框架内部调用的, 就只需要看 [事务方法代码位置] 就行
            log.info("[事务时间统计]: 事务持续时间 {} ms in 线程ID: {}, 事务方法代码位置: {}, 调用者方法代码位置: {}, 线程-事务计数: {}, 异常消息: {}",
                    duration, Thread.currentThread().getId(), methodInfo.transLocation(), methodInfo.callerLocation(), threadTransCountMap, ex.getMessage());
        } catch (Exception e) {
            log.error("[事务时间统计]: afterThrowing异常", e);
        } finally {
            // 确保在任何情况下都移除 ThreadLocal 的值
            methodInfoMap.remove(key);
        }
    }

    private Map<String, Integer> getThreadTransCountMap() {
        return methodInfoMap.keySet().stream().collect(Collectors.toMap(k -> StrUtil.subBefore(k, "-", false), v -> 1, Integer::sum));
    }

    private static String getKeyByThreadAndLocation(String className, String methodName) {
        long threadId = Thread.currentThread().getId();
        String threadName = Thread.currentThread().getName();
        String threadStr = threadId + "-" + threadName;
        return StrUtil.join("-", threadStr, getLocation(className, methodName), getCallerLocation());
    }

    /**
     * 通过堆栈跟踪获取调用方法的行号
     *
     * @return 行号，如果无法获取则返回0
     */
    private static String getCallerLocation() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        // 遍历堆栈跟踪找到目标方法的行号
        // 通常在堆栈中查找包含TransactionTimeAspect类的元素
        for (StackTraceElement element : stackTrace) {
            // 查找调用@Transactional方法的堆栈帧
            // 通常在beforeMethod之后的几个堆栈帧中
            if (element.getClassName().contains(TransactionTimeAspect.class.getName())) {
                continue;
            }

            if (element.getClassName().startsWith("com.iuin")) {
                String className = element.getClassName();
                int lineNumber = element.getLineNumber();
                return getLocation(className, lineNumber);
            }
        }

        return "未知";
    }

    private static String getLocation(String className, String methodName) {
        return getOriginClassName(className) + "#" + methodName;
    }

    private static String getLocation(String className, int lineNumber) {
        return getOriginClassName(className) + ":" + lineNumber;
    }

    private static String getOriginClassName(String className) {
        return StrUtil.subBefore(className, "$", false);
    }

    private record MethodInfo(long startTime, String transLocation, String callerLocation) {
    }

}