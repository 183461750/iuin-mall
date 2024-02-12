package com.iuin.ssoserver.maintest.test2;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 售后能力 - 退货换货 - 订单支付记录
 *
 * @author 万宁
 * @version 2.0.0
 * @date 2021-08-25
 */
@Data
@Accessors(chain = true)
public class OrderAfterSalePaymentFeignDetailVO implements Serializable {
    private static final long serialVersionUID = -1217960333018321877L;

    /**
     * 订单Id
     */
    private Long orderId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 支付记录Id
     */
    private Long paymentId;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 支付次数
     */
    private Integer batchNo;

    /**
     * 支付环节名称
     */
    private String payNode;

    /**
     * 支付比例（例：50%，payRate=0.5）
     */
    private BigDecimal payRate;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 支付积分
     */
    private BigDecimal payIntegral;

    /**
     * 资金归集模式枚举，定义在 FundModeEnum 中
     */
    private Integer fundMode;

    /**
     * 支付方式枚举，定义在 OrderPayTypeEnum 中
     */
    private Integer payType;

    /**
     * 支付方式名称
     */
    private String payTypeName;

    /**
     * 支付渠道枚举， 定义在 OrderPayChannelEnum 中
     */
    private Integer payChannel;

    /**
     * 支付渠道名称
     */
    private String payChannelName;

    /**
     * 在线支付的商户订单号（用于在线支付的退款）
     */
    private String tradeNo;

    /**
     * 结算状态, 定义在 OrderPaymentSettlementStatusEnum 中
     */
    private Integer settlementStatus;

    /**
     * 是否处于“待确认支付结果”状态
     */
    private Boolean confirmPayment;

    /**
     * 是否处于“确认到账”状态
     */
    private Boolean accomplished;

}
