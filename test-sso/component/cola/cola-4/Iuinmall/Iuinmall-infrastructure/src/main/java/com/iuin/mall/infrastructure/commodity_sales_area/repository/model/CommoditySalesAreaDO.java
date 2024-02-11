package com.iuin.mall.infrastructure.commodity_sales_area.repository.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;

import com.iuin.mall.infrastructure.BaseJpaAggregate;

/**
 * 商品销售区域实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("commodity_sales_area")
@Entity
@Table(name = "commodity_sales_area")
public class CommoditySalesAreaDO extends BaseJpaAggregate {

    /**  */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private String id;

    /** 名称 */
    private String name;

    /** 编码 */
    private String code;
}