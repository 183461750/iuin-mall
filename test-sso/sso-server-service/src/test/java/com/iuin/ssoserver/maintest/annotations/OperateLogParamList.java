package com.iuin.ssoserver.maintest.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Fa
 */
@Target( ElementType.FIELD) //注解放置的目标位置,FIELD是可注解在字段级别上
@Retention(RetentionPolicy.RUNTIME)  //注解在哪个阶段执行
public @interface OperateLogParamList {
}
