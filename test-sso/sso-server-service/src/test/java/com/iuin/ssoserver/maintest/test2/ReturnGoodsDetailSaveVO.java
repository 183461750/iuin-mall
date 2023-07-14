package com.iuin.ssoserver.maintest.test2;

import jakarta.validation.constraints.*;

import java.io.Serializable;

/**
 * 退货商品保存VO
 *
 * @author 伍将
 * @version 2.0.0
 * @date 2020/9/9
 */
public class ReturnGoodsDetailSaveVO implements Serializable {
    private static final long serialVersionUID = -4141252824248649112L;

    /**
     * 订单记录id(对应productId)
     */
    @NotNull(message = "订单记录id要大于0")
    @Positive(message = "订单记录id要大于0")
    private Long orderRecordId;

    /**
     * 订单号
     */
    @NotBlank(message = "订单号不能为空")
    private String orderNo;

    /**
     * 订单id
     */
    @NotNull(message = "订单id要大于0")
    @Positive(message = "订单id要大于0")
    private Long orderId;
    /**
     * 商城id
     */
    //@NotNull(message = "商城id要大于0")
    //@Positive(message = "商城id要大于0")
    private Long shopId;

    /**
     * 商品id/物料编号(对应productNo)
     */
    @NotNull(message = "商品Id不能为空")
    private String productId;
    /**
     * 商品skuId
     */
    private Long skuId;

    /**
     * 商品名称/物料名称、规格
     */
    @NotBlank(message = "商品名称不能为空")
    private String productName;

    /**
     * 品类
     */
    @NotBlank(message = "品类不能为空")
    private String category;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 单位
     */
    private String unit;
    /**
     * sku图片
     */
    private String skuPic;

    /**
     * 退货数量
     */
    @NotNull(message = "退货数量要大于0")
    @Positive(message = "退货数量要大于0")
    private Double returnCount;

    /**
     * 采购单价
     */
//    @NotNull(message = "采购单价要大于0")
    private Double purchasePrice;

    /**
     * 采购单价(积分)
     */
//    @NotNull(message = "采购单价(积分)要大于0")
    private Integer purchaseSinglePoint;

    /**
     * 采购数量
     */
    @NotNull(message = "采购数量要大于0")
    @Positive(message = "采购数量要大于0")
    private Double purchaseCount;

    /**
     * 退货原因
     */
    /*819版    @NotBlank(message = "退货原因不能为空")*/
    @Size(max = 100, message = "退货原因不能超过100个字符")
    private String returnReason;

    /**
     * 是否含税：0-否，1-是
     */
    @NotNull(message = "是否含税不能为空")
    @PositiveOrZero(message = "是否含税不能为空")
    private Integer isHasTax;

    /**
     * 税率
     */
    @NotNull(message = "税率不能为空")
    @PositiveOrZero(message = "税率不能为空")
    private Double taxRate;

    /**
     * 合同id
     */
    private Long contractId;

    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 商品规格
     */
    private String type;


    /**
     * 关联商品ID
     */
    private String associatedProductId;

    /**
     * 关联商品名称、规格
     */
    private String associatedProductName;

    /**
     * 关联商品规格
     */
    private String associatedType;

    /**
     * 关联商品品类
     */
    private String associatedCategory;

    /**
     * 关联商品品牌
     */
    private String associatedBrand;

    /**
     * 关联商品单位
     */
    private String associatedUnit;

    /**
     * 币种id
     */
    private Long currencyId;

    /**
     * 业务实体
     */
    private String organizeName;

    /**
     * 采购组织名称
     */
    private String buyerOrganizeName;

    /**
     * 采购员名称
     */
    private String userName;

    /**
     * 项目编号
     */
    private String projectCode;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 现货商品价格类型
     */
    private Integer spotPriceType;

    /**
     * 是否为整单退款: false为部分退款，true位整单退款
     *
     * @apiNote 用于判断用户是否选择整单退款
     */
    private Boolean entireOrderRefundBool;

    public Boolean getEntireOrderRefundBool() {
        return entireOrderRefundBool;
    }

    public void setEntireOrderRefundBool(Boolean entireOrderRefundBool) {
        this.entireOrderRefundBool = entireOrderRefundBool;
    }

    public Integer getPurchaseSinglePoint() {
        return purchaseSinglePoint;
    }

    public void setPurchaseSinglePoint(Integer purchaseSinglePoint) {
        this.purchaseSinglePoint = purchaseSinglePoint;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Integer getIsHasTax() {
        return isHasTax;
    }

    public void setIsHasTax(Integer isHasTax) {
        this.isHasTax = isHasTax;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getOrderRecordId() {
        return orderRecordId;
    }

    public void setOrderRecordId(Long orderRecordId) {
        this.orderRecordId = orderRecordId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSkuPic() {
        return skuPic;
    }

    public void setSkuPic(String skuPic) {
        this.skuPic = skuPic;
    }

    public Double getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Double returnCount) {
        this.returnCount = returnCount;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Double getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(Double purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getAssociatedProductId() {
        return associatedProductId;
    }

    public void setAssociatedProductId(String associatedProductId) {
        this.associatedProductId = associatedProductId;
    }

    public String getAssociatedProductName() {
        return associatedProductName;
    }

    public void setAssociatedProductName(String associatedProductName) {
        this.associatedProductName = associatedProductName;
    }

    public String getAssociatedType() {
        return associatedType;
    }

    public void setAssociatedType(String associatedType) {
        this.associatedType = associatedType;
    }

    public String getAssociatedCategory() {
        return associatedCategory;
    }

    public void setAssociatedCategory(String associatedCategory) {
        this.associatedCategory = associatedCategory;
    }

    public String getAssociatedBrand() {
        return associatedBrand;
    }

    public void setAssociatedBrand(String associatedBrand) {
        this.associatedBrand = associatedBrand;
    }

    public String getAssociatedUnit() {
        return associatedUnit;
    }

    public void setAssociatedUnit(String associatedUnit) {
        this.associatedUnit = associatedUnit;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getOrganizeName() {
        return organizeName;
    }

    public void setOrganizeName(String organizeName) {
        this.organizeName = organizeName;
    }

    public String getBuyerOrganizeName() {
        return buyerOrganizeName;
    }

    public void setBuyerOrganizeName(String buyerOrganizeName) {
        this.buyerOrganizeName = buyerOrganizeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getSpotPriceType() {
        return spotPriceType;
    }

    public void setSpotPriceType(Integer spotPriceType) {
        this.spotPriceType = spotPriceType;
    }
}
