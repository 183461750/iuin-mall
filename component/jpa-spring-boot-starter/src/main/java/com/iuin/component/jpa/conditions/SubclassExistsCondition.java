package com.iuin.component.jpa.conditions;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassUtil;
import com.iuin.component.jpa.annotations.ConditionalOnSubclass;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Set;

/**
 * 子类是否存在的Condition
 *
 * @author fa
 */
public class SubclassExistsCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 获取目标父类的Class（从注解参数获取）
        Class<?> parentClass = (Class<?>) metadata.getAnnotationAttributes(ConditionalOnSubclass.class.getName()).get("value");

        // 扫描类路径, 筛选出目标父类的所有子类
        Set<Class<?>> subClasses = ClassUtil.scanPackageBySuper("com.ssy.iuin", parentClass);

        // 如果存在子类，返回 true
        return CollUtil.isNotEmpty(subClasses);
    }
}
