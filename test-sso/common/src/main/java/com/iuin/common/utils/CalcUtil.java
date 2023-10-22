package com.iuin.common.utils;

import java.util.Optional;

/**
 * 计算相关工具类
 *
 * @author fa
 */
public class CalcUtil {

    /**
     * 两数相加
     */
    public static Integer add(Integer a, Integer b) {
        return Optional.ofNullable(a).orElse(0) + Optional.ofNullable(b).orElse(0);
    }

    /**
     * 两数相减
     */
    public static Integer sub(Integer a, Integer b) {
        return Optional.ofNullable(a).orElse(0) - Optional.ofNullable(b).orElse(0);
    }

    /**
     * 比较两数大小，取较大值
     */
    public static Integer max(Integer a, Integer b) {
        return Math.max(Optional.ofNullable(a).orElse(0), Optional.ofNullable(b).orElse(0));
    }

}
