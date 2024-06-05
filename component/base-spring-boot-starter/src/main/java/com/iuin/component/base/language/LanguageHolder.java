package com.iuin.component.base.language;

import cn.hutool.core.text.CharSequenceUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 国际化语言全局MessageSource
 *
 * @author 万宁
 * @version 2.0.0
 * @since 2022-02-14
 */
@Component
public class LanguageHolder {
    public static MessageSource messageSource;

    @Resource
    public MessageSource injectMessageSource;

    @PostConstruct
    public void init() {
        messageSource = injectMessageSource;
    }

    /**
     * 根据当前语言，从配置文件中查询对应的语言翻译，找不到返回默认值
     *
     * @param enumClass    枚举类型Class
     * @param defaultValue 默认值
     * @param code         属性code
     */
    public static String getTranslation(Class<?> enumClass, String defaultValue, Integer code) {
        return getTranslation(enumClass, defaultValue, String.valueOf(code));
    }

    /**
     * 根据当前语言，从配置文件中查询对应的语言翻译，找不到返回默认值
     *
     * @param enumClass    枚举类型Class
     * @param defaultValue 默认值
     * @param code         属性code
     */
    public static String getTranslation(Class<?> enumClass, String defaultValue, Long code) {
        return getTranslation(enumClass, defaultValue, String.valueOf(code));
    }

    /**
     * 根据当前语言，从配置文件中查询对应的语言翻译，找不到返回默认值
     *
     * @param enumClass    枚举类型Class
     * @param defaultValue 默认值
     * @param code         属性code
     */
    public static String getTranslation(Class<?> enumClass, String defaultValue, String code) {
        return getTranslation(enumClass, defaultValue, code, "");
    }

    /**
     * 根据当前语言，从配置文件中查询对应的语言翻译，找不到返回默认值
     *
     * @param enumClass    枚举类型Class
     * @param defaultValue 默认值
     * @param code         属性code
     * @param suffix       扩展名(对应properties中枚举项的后缀，如果枚举项后面没有第三级，不需要传)
     *                     1：例如：MessageNoticeEnum.commodity_apply，不需要传;
     *                     2：例如：MessageNoticeEnum.commodity_apply.title，那就传"title";
     *                     3：例如：MessageNoticeEnum.commodity_apply.title.template，那就传"title","template";
     *                     4：以此类推
     */
    public static String getTranslation(Class<?> enumClass, String defaultValue, Integer code, String... suffix) {
        return getTranslation(enumClass, defaultValue, String.valueOf(code), suffix);
    }

    /**
     * 根据当前语言，从配置文件中查询对应的语言翻译，找不到返回默认值
     *
     * @param enumClass    枚举类型Class
     * @param defaultValue 默认值
     * @param code         属性code
     * @param suffix       扩展名(对应properties中枚举项的后缀，如果枚举项后面没有第三级，不需要传)
     *                     1：例如：MessageNoticeEnum.commodity_apply，不需要传;
     *                     2：例如：MessageNoticeEnum.commodity_apply.title，那就传"title";
     *                     3：例如：MessageNoticeEnum.commodity_apply.title.template，那就传"title","template";
     *                     4：以此类推
     */
    public static String getTranslation(Class<?> enumClass, String defaultValue, String code, String... suffix) {
        if (Objects.isNull(code)) {
            return defaultValue;
        }

        String key = enumClass.getSimpleName().concat(".").concat(code);
        for (String s : suffix) {
            if (CharSequenceUtil.isNotBlank(s)) {
                key = key.concat(".").concat(s);
            }
        }

        try {
            return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
        } catch (Exception ignored) {
            return defaultValue;
        }
    }
}
