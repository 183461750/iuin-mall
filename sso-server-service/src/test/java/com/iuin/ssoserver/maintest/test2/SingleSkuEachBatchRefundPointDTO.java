package com.iuin.ssoserver.maintest.test2;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 单sku每批的退款金额
 *
 * @author fa
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class SingleSkuEachBatchRefundPointDTO extends SkuIdPayIdDTO {

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 支付批次号
     */
    private Integer payBatchNo;

    private Integer refundPoint;

    /**
     * 采购单价(积分)
     */
    private Integer purchaseSinglePoint;

    /**
     * 退货数量
     */
    private Integer returnCount;

    public SingleSkuEachBatchRefundPointDTO(Integer purchaseSinglePoint, Integer returnCount, Integer refundPoint, Long orderId, Long skuId, Long payId, Integer payBatchNo) {
        this.purchaseSinglePoint = purchaseSinglePoint;
        this.returnCount = returnCount;
        this.refundPoint = refundPoint;
        this.orderId = orderId;
        this.setSkuId(skuId);
        this.setPayId(payId);
        this.payBatchNo = payBatchNo;
    }

    /**
     * 获得计算后的退款积分值
     */
    public static int getCalcRefundPoint(Map<Long, List<SingleSkuEachBatchRefundPointDTO>> refundPointMap, Long skuId, Long paymentId) {
        return Optional.ofNullable(refundPointMap.get(skuId)).flatMap(dtoList -> dtoList.stream().filter(dto -> Objects.equals(dto.getPayId(), paymentId)).findFirst().map(SingleSkuEachBatchRefundPointDTO::getRefundPoint)).orElse(0);
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

