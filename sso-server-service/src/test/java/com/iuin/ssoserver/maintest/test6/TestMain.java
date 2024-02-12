package com.iuin.ssoserver.maintest.test6;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.iuin.common.utils.CalcUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>TODO</p>
 *
 * @author fa
 */
public class TestMain {

    public static void main(String[] args) {
        String jsonStr = "[{\"skuCode\":\"75117\",\"receivedQty\":1,\"inventorySts\":\"AVAILABLE\"},{\"skuCode\":\"75117\",\"receivedQty\":1,\"inventorySts\":\"NOT_AVAILABLE\"}]";
        String omsHandleResult = toOmsHandleResult(JSONUtil.toList(jsonStr, OmsDeliveryGoodsDetailBO.class));

        System.out.println(omsHandleResult);
    }

    public static String toOmsHandleResult(List<OmsDeliveryGoodsDetailBO> detailBOList) {
        detailBOList = Optional.ofNullable(detailBOList).orElseGet(ArrayList::new);
        Map<String, Integer> inventoryStsReceivedQtyMap = detailBOList.stream().filter(detailBO -> InventoryStsEnum.AVAILABLE.getCode().equals(detailBO.getInventorySts())).collect(Collectors.toMap(OmsDeliveryGoodsDetailBO::getInventorySts, OmsDeliveryGoodsDetailBO::getReceivedQty, CalcUtil::add));
        inventoryStsReceivedQtyMap.put(InventoryStsEnum.NOT_AVAILABLE.getCode(), detailBOList.stream().filter(detailBO -> !InventoryStsEnum.AVAILABLE.getCode().equals(detailBO.getInventorySts())).map(OmsDeliveryGoodsDetailBO::getReceivedQty).reduce(CalcUtil::add).orElse(0));
        return inventoryStsReceivedQtyMap.keySet().stream().filter(inventorySts -> Optional.ofNullable(inventoryStsReceivedQtyMap.get(inventorySts)).orElse(0) > 0).sorted(Comparator.comparing(inventorySts -> InventoryStsEnum.getEnum(inventorySts).ordinal())).map(inventorySts -> InventoryStsEnum.getName(inventorySts) + ": " + inventoryStsReceivedQtyMap.getOrDefault(inventorySts, 0)).reduce((s, s1) -> StrUtil.join(", ", s, s1)).orElse("");
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
            return Arrays.stream(InventoryStsEnum.values()).filter(o -> o.getCode().equals(code)).map(InventoryStsEnum::getName).findFirst().orElse("");
        }

        public static InventoryStsEnum getEnum(String code) {
            return Arrays.stream(InventoryStsEnum.values()).filter(o -> o.getCode().equals(code)).findFirst().orElse(UNKNOWN);
        }

    }

    /**
     * oms商品发货详情
     *
     * @author fa
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
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
