package com.iuin.ssoserver.maintest.test2;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONUtil;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>TODO</p>
 *
 * @author fa
 */
public class TestMain {

    public static void main(String[] args) {

        String jsonStr = "[{\"isHasTax\":0,\"taxRate\":0,\"returnCount\":1,\"purchaseSinglePoint\":30,\"spotPriceType\":2,\"returnReason\":\"卖家发错货\",\"returnReasonCode\":1,\"orderId\":1999,\"orderRecordId\":2376,\"orderNo\":\"DKFWRN5SW\",\"productId\":\"381\",\"productName\":\"常用的纯积分商品/容量1:50ml\",\"category\":\"佰草集\",\"brand\":\"GCC-品牌\",\"unit\":\"个\",\"purchasePrice\":0,\"purchaseCount\":1,\"skuPic\":\"https://iuin01.oss-cn-shenzhen.aliyuncs.com/100629235dcf232e845cd97c8b7548e94e0c5.jpg\",\"contractId\":0,\"contractNo\":\"\",\"associated\":\"\",\"associatedProductId\":\"\",\"associatedProductName\":\"\",\"associatedType\":\"\",\"associatedCategory\":\"\",\"associatedBrand\":\"\",\"associatedUnit\":\"个\",\"skuId\":381,\"productWelfareList\":null,\"quantity\":\"1\"}]";

        List<ReturnGoodsDetailSaveVO> goodsList = JSONUtil.toBean(jsonStr, new TypeReference<>() {
        }, true);

        Map<Long, List<OrderAfterSalePaymentFeignDetailVO>> ordersPayMap = goodsList.stream().collect(Collectors.toMap(ReturnGoodsDetailSaveVO::getOrderId, o -> List.of(new OrderAfterSalePaymentFeignDetailVO().setPayRate(BigDecimal.ONE).setBatchNo(1))));

        Map<Long, List<SingleSkuEachBatchRefundPointDTO>> longListMap = calcEachSkuEachBatchRefundPoint(goodsList, ordersPayMap);

        System.out.println("结果: " + JSONUtil.toJsonStr(longListMap));

    }

    /**
     * 计算每个sku商品的每批次所需退还的积分值
     */
    public static Map<Long, List<SingleSkuEachBatchRefundPointDTO>> calcEachSkuEachBatchRefundPoint(List<ReturnGoodsDetailSaveVO> goodsList, Map<Long, List<OrderAfterSalePaymentFeignDetailVO>> ordersPayMap) {
        Map<Long, List<SingleSkuEachBatchRefundPointDTO>> skuIdRefundPointListMap = Optional.ofNullable(goodsList).map(validGoodsList -> validGoodsList.stream().map(goodsDetail -> Optional.ofNullable(ordersPayMap.get(goodsDetail.getOrderId())).map(list -> list.stream().map(pay -> {
            // 计算当前sku商品的当前批次的退款积分
            int refundPoint = Optional.ofNullable(goodsDetail.getPurchaseSinglePoint()).map(singlePoint -> NumberUtil.mul(NumberUtil.mul(goodsDetail.getReturnCount(), goodsDetail.getPurchaseSinglePoint()), pay.getPayRate().doubleValue()).intValue()).orElse(0);
            return new SingleSkuEachBatchRefundPointDTO(Optional.ofNullable(goodsDetail.getPurchaseSinglePoint()).orElse(0), Optional.ofNullable(goodsDetail.getReturnCount()).map(Double::intValue).orElse(0), refundPoint, goodsDetail.getOrderId(), goodsDetail.getSkuId(), pay.getPaymentId(), pay.getBatchNo());
        }).collect(Collectors.toList())).orElseGet(ArrayList::new)).collect(Collectors.toList())).orElseGet(ArrayList::new).stream().flatMap(Collection::stream).collect(Collectors.toMap(SkuIdPayIdDTO::getSkuId, CollUtil::newArrayList, CollUtil::unionAll));

        // 计算退款积分和 -- 排除最大批次
        Map<Long, Integer> skuIdRefundPointMap = refundPointSumFilterMaxBatch(skuIdRefundPointListMap, ordersPayMap);

        // 计算每个sku商品最后一批所需退还的积分值
        skuIdRefundPointListMap.values().forEach(dtoList -> dtoList.stream().max(Comparator.comparingInt(SingleSkuEachBatchRefundPointDTO::getPayBatchNo)).ifPresent(dto -> dto.setRefundPoint(calcLastBatchRefundPoint(skuIdRefundPointMap, dto))));

        return skuIdRefundPointListMap;
    }

    /**
     * 计算最后一批次的退款积分
     */
    private static Integer calcLastBatchRefundPoint(Map<Long, Integer> skuIdRefundPointMap, SingleSkuEachBatchRefundPointDTO dto) {
        return Math.max((dto.getPurchaseSinglePoint() * dto.getReturnCount()) - skuIdRefundPointMap.get(dto.getSkuId()), 0);
    }

    /**
     * 计算退款积分和 -- 排除最大批次
     */
    private static Integer refundPointSumFilterMaxBatch(Long skuId, Map<Long, List<SingleSkuEachBatchRefundPointDTO>> skuIdRefundPointListMap, Map<Long, List<OrderAfterSalePaymentFeignDetailVO>> ordersPayMap) {
        return Optional.ofNullable(skuIdRefundPointListMap.get(skuId)).orElseGet(ArrayList::new).stream().filter(dto -> dto.getPayBatchNo() < findMaxBatchNo(dto.getOrderId(), ordersPayMap)).mapToInt(SingleSkuEachBatchRefundPointDTO::getRefundPoint).sum();
    }

    /**
     * 退款积分求和 -- 排除最大批次
     */
    private static Map<Long, Integer> refundPointSumFilterMaxBatch(Map<Long, List<SingleSkuEachBatchRefundPointDTO>> skuIdRefundPointListMap, Map<Long, List<OrderAfterSalePaymentFeignDetailVO>> ordersPayMap) {
        return skuIdRefundPointListMap.keySet().stream().collect(Collectors.toMap(Function.identity(), skuId -> refundPointSumFilterMaxBatch(skuId, skuIdRefundPointListMap, ordersPayMap), Math::min));
    }

    /**
     * 找最大批次
     */
    private static Integer findMaxBatchNo(Long orderId, Map<Long, List<OrderAfterSalePaymentFeignDetailVO>> ordersPayMap) {
        return Optional.ofNullable(ordersPayMap.get(orderId)).map(list -> list.stream().map(OrderAfterSalePaymentFeignDetailVO::getBatchNo).max(Comparator.comparingInt(Integer::intValue)).orElse(0)).orElse(0);
    }

}
