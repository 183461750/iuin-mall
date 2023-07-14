package com.iuin.ssoserver.maintest.test7;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iuin.common.utils.CalcUtil;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.assertj.core.util.Lists;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>TODO</p>
 *
 * @author fa
 */
public class TestMain {

    public static void main(String[] args) {

        try {
            List<OmsDeliveryGoodsDetailBO> detailBOList = new ObjectMapper().readValue("null", new TypeReference<List<OmsDeliveryGoodsDetailBO>>() {
            });

            System.out.println(detailBOList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Map<String, Integer> stringIntegerMap = toOmsHandleResult(Lists.newArrayList((OmsDeliveryGoodsDetailBO) null));

        System.out.println(stringIntegerMap);
    }

    public static Map<String, Integer> toOmsHandleResult(List<OmsDeliveryGoodsDetailBO> detailBOList) {
        return Optional.ofNullable(detailBOList).orElseGet(ArrayList::new).stream().filter(Objects::nonNull).collect(Collectors.toMap(detailBO -> InventoryStsEnum.getName(detailBO.getInventorySts()), OmsDeliveryGoodsDetailBO::getReceivedQty, CalcUtil::add));
    }

    @Getter
    public enum InventoryStsEnum {

        /**
         * OMS名称: 可售
         */
        AVAILABLE("AVAILABLE", "可售"),
        /**
         * OMS名称: 残次
         */
        NOT_AVAILABLE("NOT_AVAILABLE", "不可售"),
        /**
         * OMS名称: 冻结
         */
        FROZEN("FROZEN", "冻结"),
        /**
         * OMS名称: 临期
         */
        ADVENT("ADVENT", "临期"),
        /**
         * OMS名称: 过期
         */
        INVALID("INVALID", "过期"),

        UNKNOWN("UNKNOWN", "未知"),

        ;

        private final String code;
        private final String name;

        InventoryStsEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public static String getName(String code) {
            return Arrays.stream(InventoryStsEnum.values()).filter(o -> o.getCode().equals(code)).map(InventoryStsEnum::getName).findFirst().orElse(UNKNOWN.getName());
        }

        public static InventoryStsEnum getEnum(String code) {
            return Arrays.stream(InventoryStsEnum.values()).filter(o -> o.getCode().equals(code)).findFirst().orElse(UNKNOWN);
        }

    }

    @Data
    @Accessors(chain = true)
    public static class OmsDeliveryGoodsDetailBO {

        /**
         * SKU 编码
         */
        private String skuCode;

        /**
         * 库存状态
         */
        private String inventorySts;

        /**
         * 入库数量
         */
        private Integer receivedQty;

    }

}
