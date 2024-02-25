package com.iuin.component.yapi.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *  项目部署-yapi-token列表
 * @author ds
 * @since 2023/1/13
 * @version 2.0.0
 */
@Setter
@Getter
public class YapiBaseResp<T> implements Serializable {
    private static final long serialVersionUID = -5377008952507889193L;

    /**
     * code
     */
    private Integer errcode;

    /**
     * 信息
     */
    private String errmsg;

    /**
     * 数据
     */
    private T data;
}
