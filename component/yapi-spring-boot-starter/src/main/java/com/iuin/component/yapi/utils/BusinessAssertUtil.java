package com.iuin.component.yapi.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.iuin.common.enums.ResponseCodeEnum;
import com.iuin.common.model.exceptions.BusinessException;
import org.slf4j.Logger;

import java.util.*;

/**
 * 业务断言类
 *
 * @author whm
 **/
public abstract class BusinessAssertUtil {
    private static final ResponseCodeEnum DEFAULT_RESPONSE_CODE_ENUM = ResponseCodeEnum.BUSINESS_ERROR;

    public static void isTrue(boolean expression) {
        isTrue(expression, DEFAULT_RESPONSE_CODE_ENUM);
    }

    public static void isTrue(boolean expression, ResponseCodeEnum responseCodeEnum) {
        if (!expression) {
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void isTrue(boolean expression, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        if (!expression) {
            log.error(template, params);
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void isFalse(boolean expression) {
        isFalse(expression, DEFAULT_RESPONSE_CODE_ENUM);
    }

    public static void isFalse(boolean expression, ResponseCodeEnum responseCodeEnum) {
        if (expression) {
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void isFalse(boolean expression, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        if (expression) {
            log.error(template, params);
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void isNull(Object object) {
        isNull(object, DEFAULT_RESPONSE_CODE_ENUM);
    }

    public static void isNull(Object object, ResponseCodeEnum responseCodeEnum) {
        if (object != null) {
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void isNull(Object object, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        if (object != null) {
            log.error(template, params);
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void notNull(Object object) {
        notNull(object, DEFAULT_RESPONSE_CODE_ENUM);
    }

    public static void notNull(Object object, ResponseCodeEnum responseCodeEnum) {
        if (object == null) {
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void notNull(Object object, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        if (object == null) {
            log.error(template, params);
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void notBlank(CharSequence str) {
        notBlank(str, DEFAULT_RESPONSE_CODE_ENUM);
    }

    public static void notBlank(CharSequence str, ResponseCodeEnum responseCodeEnum) {
        if (StrUtil.isBlank(str)) {
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void notBlank(CharSequence str, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        if (StrUtil.isBlank(str)) {
            log.error(template, params);
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void isBlank(CharSequence str) {
        isBlank(str, DEFAULT_RESPONSE_CODE_ENUM);
    }

    public static void isBlank(CharSequence str, ResponseCodeEnum responseCodeEnum) {
        if (StrUtil.isNotBlank(str)) {
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void isBlank(CharSequence str, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        if (StrUtil.isNotBlank(str)) {
            log.error(template, params);
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void isAllBlank(CharSequence... str) {
        isAllBlank(str, DEFAULT_RESPONSE_CODE_ENUM);
    }

    public static void isAllBlank(CharSequence[] str, ResponseCodeEnum responseCodeEnum) {
        if (!StrUtil.isAllBlank(str)) {
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void isAllBlank(CharSequence[] str, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        if (!StrUtil.isAllBlank(str)) {
            log.error(template, params);
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void allNotBlank(CharSequence... str) {
        allNotBlank(str, DEFAULT_RESPONSE_CODE_ENUM);
    }

    public static void allNotBlank(CharSequence[] str, ResponseCodeEnum responseCodeEnum) {
        if (!StrUtil.isAllNotBlank(str)) {
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void allNotBlank(CharSequence[] str, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        if (!StrUtil.isAllNotBlank(str)) {
            log.error(template, params);
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void notAllBlank(CharSequence... str) {
        notAllBlank(str, DEFAULT_RESPONSE_CODE_ENUM);
    }

    public static void notAllBlank(CharSequence[] str, ResponseCodeEnum responseCodeEnum) {
        if (StrUtil.isAllNotBlank(str)) {
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void notAllBlank(CharSequence[] str, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        if (StrUtil.isAllNotBlank(str)) {
            log.error(template, params);
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void notEmpty(Collection<?> collection) {
        notEmpty(collection, DEFAULT_RESPONSE_CODE_ENUM);
    }

    public static void notEmpty(Collection<?> collection, ResponseCodeEnum responseCodeEnum) {
        if (CollUtil.isEmpty(collection)) {
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void notEmpty(Collection<?> collection, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        if (CollUtil.isEmpty(collection)) {
            log.error(template, params);
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void isEmpty(Collection<?> collection) {
        isEmpty(collection, DEFAULT_RESPONSE_CODE_ENUM);
    }

    public static void isEmpty(Collection<?> collection, ResponseCodeEnum responseCodeEnum) {
        if (!CollUtil.isEmpty(collection)) {
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void isEmpty(Collection<?> collection, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        if (!CollUtil.isEmpty(collection)) {
            log.error(template, params);
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void notEmpty(Map<?, ?> map) {
        notEmpty(map, DEFAULT_RESPONSE_CODE_ENUM);
    }

    public static void notEmpty(Map<?, ?> map, ResponseCodeEnum responseCodeEnum) {
        if (CollUtil.isEmpty(map)) {
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void notEmpty(Map<?, ?> map, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        if (CollUtil.isEmpty(map)) {
            log.error(template, params);
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void isEmpty(Map<?, ?> map) {
        isEmpty(map, DEFAULT_RESPONSE_CODE_ENUM);
    }

    public static void isEmpty(Map<?, ?> map, ResponseCodeEnum responseCodeEnum) {
        if (!CollUtil.isEmpty(map)) {
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void isEmpty(Map<?, ?> map, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        if (!CollUtil.isEmpty(map)) {
            log.error(template, params);
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void notEmpty(Object[] array) {
        notEmpty(array, DEFAULT_RESPONSE_CODE_ENUM);
    }

    public static void notEmpty(Object[] array, ResponseCodeEnum responseCodeEnum) {
        if (ObjectUtil.isEmpty(array)) {
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void notEmpty(Object[] array, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        if (ObjectUtil.isEmpty(array)) {
            log.error(template, params);
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void isEmpty(Object[] array) {
        isEmpty(array, DEFAULT_RESPONSE_CODE_ENUM);
    }

    public static void isEmpty(Object[] array, ResponseCodeEnum responseCodeEnum) {
        if (!ObjectUtil.isEmpty(array)) {
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void isEmpty(Object[] array, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        if (!ObjectUtil.isEmpty(array)) {
            log.error(template, params);
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void allNotNull(Object... objects) {
        allNotNull(Arrays.asList(objects), DEFAULT_RESPONSE_CODE_ENUM);
    }

    public static void allNotNull(List<Object> objectList, ResponseCodeEnum responseCodeEnum) {
        if (objectList.stream().anyMatch(Objects::isNull)) {
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void allNotNull(List<Object> objectList, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        if (objectList.stream().anyMatch(Objects::isNull)) {
            log.error(template, params);
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void allNull(Object... objects) {
        allNull(Arrays.asList(objects), DEFAULT_RESPONSE_CODE_ENUM);
    }

    public static void allNull(List<Object> objectList, ResponseCodeEnum responseCodeEnum) {
        if (objectList.stream().anyMatch(Objects::nonNull)) {
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void allNull(List<Object> objectList, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        if (objectList.stream().anyMatch(Objects::nonNull)) {
            log.error(template, params);
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void notAllNull(Object... objects) {
        notAllNull(Arrays.asList(objects), DEFAULT_RESPONSE_CODE_ENUM);
    }

    public static void notAllNull(List<Object> objectList, ResponseCodeEnum responseCodeEnum) {
        if (objectList.stream().allMatch(Objects::isNull)) {
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void notAllNull(List<Object> objectList, ResponseCodeEnum responseCodeEnum, Logger log, String template, Object... params) {
        if (objectList.stream().allMatch(Objects::isNull)) {
            log.error(template, params);
            throw new BusinessException(responseCodeEnum);
        }
    }
}
