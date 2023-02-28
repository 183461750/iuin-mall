package com.iuin.ssoserver.maintest.test1;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReflectUtil;
import com.iuin.ssoserver.maintest.annotations.OperateLogParamBillId;
import com.iuin.ssoserver.maintest.annotations.OperateLogParamList;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * <p>TODO</p>
 *
 * @author fa
 */
public class TestMain1 {

    public static void main(String[] args) {
        System.out.println(getBillId(new TestDemo(List.of(new TestObj(3L)))));
    }

    private static Long getBillId(Object param) {
        if (param instanceof Long) {
            return  (Long) param;
        }
        Long res = 0L;
        Field[] fields = ReflectUtil.getFields(param.getClass());
        Field field = Stream.of(fields).filter(f -> f.isAnnotationPresent(OperateLogParamBillId.class)).findFirst().orElse(null);
        //  case 1: 直接结束 检测对象字段值没有使用OperateLogParamBillId，

        try{
            Object value =  ReflectUtil.getFieldValue(param, field);
            if (Objects.nonNull(value) && value instanceof Long) {
                res = (Long) value;
            }

            // 批量操作的处理
            if (Objects.isNull(field)) {
                Field listField = Stream.of(fields).filter(f -> f.isAnnotationPresent(OperateLogParamList.class)).findFirst().orElse(null);
                Object listValue =  ReflectUtil.getFieldValue(param, listField);
                if (Objects.nonNull(listValue) && listValue instanceof List) {
                    List<?> list = (List<?>) listValue;
                    if (CollUtil.isNotEmpty(list)) {
                        // 从list中找一个结果即可
                        res = list.stream().findFirst().map(TestMain1::getBillId).orElse(null);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}
