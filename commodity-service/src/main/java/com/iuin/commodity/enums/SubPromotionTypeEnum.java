package com.iuin.commodity.enums;

import com.iuin.component.base.language.LanguageHolder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.iuin.commodity.enums.ThirdPromotionTypeEnum.*;

/**
 * 营销（促销）二级类型枚举
 *
 * @author 万宁
 * @version 3.0.0
 * @since 2023-08-18
 */
@Getter
@RequiredArgsConstructor
public enum SubPromotionTypeEnum {
    NONE(0, "", Collections.emptyList()),

    QUANTITY_DIRECT(1, "满量减", Collections.emptyList()),

    QUANTITY_DISCOUNT(2, "满量折", Collections.emptyList()),

    AMOUNT_DIRECT(3, "满额减", Collections.emptyList()),

    AMOUNT_DISCOUNT(4, "满额折", Collections.emptyList()),

    GIFT_AMOUNT(5, "满额赠", Arrays.asList(COMMODITY_GIFT, COUPON_GIFT)),

    GIFT_COMMODITY(6, "买商品赠", Arrays.asList(COMMODITY_GIFT, COUPON_GIFT)),

    EXCHANGE_AMOUNT(7, "满额换购", Collections.emptyList()),

    EXCHANGE_COMMODITY(8, "买商品换购", Collections.emptyList());

    private final Integer code;
    private final String name;

    /**
     * 关联的三级类型
     */
    private final List<ThirdPromotionTypeEnum> thirdTypes;

    public String getName() {
        return LanguageHolder.getTranslation(this.getClass(), this.name, this.code);
    }

    public static String getNameByCode(Integer code) {
        return Objects.isNull(code) ? "" : Arrays.stream(SubPromotionTypeEnum.values()).filter(e -> e.getCode().equals(code)).map(SubPromotionTypeEnum::getName).findFirst().orElse("");
    }

    public static SubPromotionTypeEnum parse(Integer code) {
        return Objects.isNull(code) ? NONE : Arrays.stream(SubPromotionTypeEnum.values()).filter(e -> e.getCode().equals(code)).findFirst().orElse(NONE);
    }
}
