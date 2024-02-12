package com.iuin.component.pluggable_annotation.version;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.SOURCE) //只在编译期有效，最终不会打进class文件中
@Target({ElementType.FIELD}) //仅允许作用于类属性之上
public @interface TrisceliVersion {
}