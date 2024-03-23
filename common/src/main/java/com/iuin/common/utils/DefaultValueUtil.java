package com.iuin.common.utils;

import cn.hutool.core.util.ReflectUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * 默认值工具类
 *
 * @author fa
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultValueUtil {

    /**
     * 初始化默认数据<br/>
     * {@code DefaultValueUtil.initDefaultData(this, Fields::values);}
     *
     * @see FieldNameConstants#asEnum() = true
     */
    public static void initDefaultData(Object obj, Supplier<Enum<?>[]> enumSupplier) {
        Stream.of(enumSupplier.get()).forEach(fields -> {
            Field field = ReflectUtil.getField(obj.getClass(), fields.name());
            Object fieldValue = ReflectUtil.getFieldValue(obj, field);
            String fieldTypeName = field.getType().getName();

            if (Objects.nonNull(fieldValue)) {
                // 非空的字段，直接返回
                return;
            }

            // 字符串为空的字段，设置为空字符串
            if (String.class.getName().equals(fieldTypeName)) {
                ReflectUtil.setFieldValue(obj, field, "");
            }
            // Long类型为空的字段，设置为0L
            if (Long.class.getName().equals(fieldTypeName)) {
                ReflectUtil.setFieldValue(obj, field, 0L);
            }
            // Integer类型为空的字段，设置为0
            if (Integer.class.getName().equals(fieldTypeName)) {
                ReflectUtil.setFieldValue(obj, field, 0);
            }
        });
    }

}
