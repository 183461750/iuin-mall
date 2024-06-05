package com.iuin.commodity.enums;

import com.iuin.component.base.language.LanguageHolder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

/**
 * 营销（促销）三级级类型枚举
 *
 * @author 万宁
 * @version 3.0.0
 * @since 2023-08-18
 */
@Getter
@RequiredArgsConstructor
public enum ThirdPromotionTypeEnum {

    NONE(0, ""),

    COMMODITY_GIFT(1, "赠商品"),

    COUPON_GIFT(2, "赠优惠券");

    private final Integer code;
    private final String name;

    public String getName() {
        return LanguageHolder.getTranslation(this.getClass(), this.name, this.code);
    }

    public static String getNameByCode(Integer code) {
        return Objects.isNull(code) ? "" : Arrays.stream(ThirdPromotionTypeEnum.values()).filter(e -> e.getCode().equals(code)).map(ThirdPromotionTypeEnum::getName).findFirst().orElse("");
    }

    public static ThirdPromotionTypeEnum parse(Integer code) {
        return Objects.isNull(code) ? NONE : Arrays.stream(ThirdPromotionTypeEnum.values()).filter(e -> e.getCode().equals(code)).findFirst().orElse(NONE);
    }
}
