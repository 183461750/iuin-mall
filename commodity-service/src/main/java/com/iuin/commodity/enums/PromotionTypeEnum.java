package com.iuin.commodity.enums;

import com.iuin.component.base.language.LanguageHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.iuin.commodity.enums.SubPromotionTypeEnum.*;

/**
 * 营销（促销）主类型枚举
 *
 * @author 万宁
 * @version 3.0.0
 * @since 2023-08-17
 */
@Getter
@AllArgsConstructor
public enum PromotionTypeEnum {

    NONE(0, "", Collections.emptyList(), Collections.emptyList()),

    DIRECT(1, "直降促销", Collections.emptyList(), Collections.emptyList()),

    DISCOUNT(2, "折扣促销", Collections.emptyList(), Collections.emptyList()),

    QUANTITY(3, "满量促销", Collections.emptyList(), Arrays.asList(QUANTITY_DIRECT, QUANTITY_DISCOUNT)),

    AMOUNT(4, "满额促销", Collections.emptyList(), Arrays.asList(AMOUNT_DIRECT, AMOUNT_DISCOUNT)),

    GIFT(5, "赠送促销", Collections.emptyList(), Arrays.asList(GIFT_AMOUNT, GIFT_COMMODITY)),

    EXCHANGE(6, "换购", Collections.emptyList(), Arrays.asList(EXCHANGE_AMOUNT, EXCHANGE_COMMODITY));

    static {
        DIRECT.combineTypes = Arrays.asList(QUANTITY, AMOUNT, GIFT, EXCHANGE);
        DISCOUNT.combineTypes = Arrays.asList(QUANTITY, AMOUNT, GIFT, EXCHANGE);
        QUANTITY.combineTypes = Arrays.asList(DIRECT, DISCOUNT, GIFT, EXCHANGE);
        AMOUNT.combineTypes = Arrays.asList(DIRECT, DISCOUNT, GIFT, EXCHANGE);
        GIFT.combineTypes = Arrays.asList(DIRECT, DISCOUNT, QUANTITY, AMOUNT, EXCHANGE);
        EXCHANGE.combineTypes = Arrays.asList(DIRECT, DISCOUNT, QUANTITY, AMOUNT, GIFT);
    }

    private final Integer code;
    private final String name;

    /**
     * 可叠加的其他类型
     */
    private List<PromotionTypeEnum> combineTypes;

    /**
     * 二级类型
     */
    private final List<SubPromotionTypeEnum> subTypes;


    public String getName() {
        return LanguageHolder.getTranslation(this.getClass(), this.name, this.code);
    }

    public static String getNameByCode(Integer code) {
        return Objects.isNull(code) ? "" : Arrays.stream(PromotionTypeEnum.values()).filter(e -> e.getCode().equals(code)).map(PromotionTypeEnum::getName).findFirst().orElse("");
    }

    public static PromotionTypeEnum parse(Integer code) {
        return Objects.isNull(code) ? PromotionTypeEnum.NONE : Arrays.stream(PromotionTypeEnum.values()).filter(e -> e.getCode().equals(code)).findFirst().orElse(NONE);
    }

    public static List<PromotionTypeEnum> getDirectCombineTypes() {
        return Arrays.asList(QUANTITY, AMOUNT, GIFT, EXCHANGE);
    }

}
