package com.iuin.commodity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author fa
 */
@TableName(value = "com_address")
@Data
@FieldNameConstants
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

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}