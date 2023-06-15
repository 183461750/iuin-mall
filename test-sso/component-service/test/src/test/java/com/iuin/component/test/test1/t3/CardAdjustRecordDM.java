package com.iuin.component.test.test1.t3;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author fa
 */
public class CardAdjustRecordDM {

    public static void main(String[] args) {
        String newAdjustNo = "TZ202306120011";
        String genAdjustNo = genAdjustNo(AdjustTypeEnum.single, newAdjustNo);

        System.out.println(genAdjustNo);
    }

    /**
     * 生成批次号
     * TZ20221101001/PT20221101001
     */
    public static String genAdjustNo(AdjustTypeEnum type) {
        return genAdjustNo(type, null);
    }

    /**
     * 生成批次号
     * TZ20221101001/PT20221101001
     */
    public static String genAdjustNo(AdjustTypeEnum type, String newestAdjustNo) {
        return genAdjustNo(type, newestAdjustNo, 0);
    }

    /**
     * 生成批次号
     * TZ20221101001/PT20221101001
     */
    public static String genAdjustNo(AdjustTypeEnum type, String newestAdjustNo, Integer startNum) {
        String prefix = type.prefix + DateUtil.date().toString("yyyyMMdd");
        String suffix = StrUtil.isNotBlank(newestAdjustNo) && newestAdjustNo.startsWith(prefix) ? String.valueOf(NumberUtil.parseInt(newestAdjustNo.substring(prefix.length())) + startNum) : "000" + startNum;
        return prefix + StrUtil.fillBefore(String.valueOf(NumberUtil.add(suffix, "1").intValue()), '0', 4);
    }


    /**
     * 调账类型：1=单个调账；2=批量调账
     * @author fa
     */
    @Getter
    @AllArgsConstructor
    public enum AdjustTypeEnum {
        single(1, "TZ"),
        batch(2, "PT"),
        ;
        private final Integer code;
        private final String prefix;
    }

    /**
     * 类型：1=消费；2=退款；3=调账
     * @author fa
     */
    @Getter
    @AllArgsConstructor
    public enum ConsumeRecordTypeEnum {
        consume(1, "消费"),
        refund(2, "退款"),
        adjust(3, "调账"),
        ;
        private final Integer code;
        private final String name;
    }

}
