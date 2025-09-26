package com.iuin.component.jpa.annotations;

import com.iuin.component.jpa.conditions.SubclassExistsCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * 子类条件注解, 存在子类则满足条件
 *
 * @author fa
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(SubclassExistsCondition.class)
public @interface ConditionalOnSubclass {
    /**
     * 需要校验子类的父类
     */
    Class<?> value();
}
