package com.iuin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类常量注解，用于在编译期生成包含类名常量的静态内部类
 * 例如：对于UserDTO类，会生成UserDTO.Clazz.USER_DTO = "UserDTO"
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ClassConstant {
    // 可以添加配置参数，如果需要的话
}