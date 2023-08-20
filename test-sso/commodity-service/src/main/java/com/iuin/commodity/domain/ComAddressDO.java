package com.iuin.commodity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName com_address
 */
@TableName(value ="com_address")
@Data
public class ComAddressDO implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 城市
     */
    private String city;

    /**
     * 地址
     */
    private String address;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}