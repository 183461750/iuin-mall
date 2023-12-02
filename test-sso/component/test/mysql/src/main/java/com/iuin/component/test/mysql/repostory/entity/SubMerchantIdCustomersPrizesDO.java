package com.iuin.component.test.mysql.repostory.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.Date;

/**
 * 用户中奖表
 * @author fa
 */
@Data
@FieldNameConstants
@TableName(value = "sub_1_customers_prizes")
public class SubMerchantIdCustomersPrizesDO {

    private Integer id;

    private Integer appId;

    private Integer customerId;

    private Integer pageId;

    private Integer activityId;

    private Integer prizeId;

    private String codeid;

    private String code;

    private String channel;

    private Integer status;

    private String mobile;

    private Date createdAt;

    private Date updatedAt;


}