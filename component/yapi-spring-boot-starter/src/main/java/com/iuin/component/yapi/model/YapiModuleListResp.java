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
public class YapiModuleListResp implements Serializable {
    private static final long serialVersionUID = -9178291311893057238L;

    /**
     * 模块名称
     */
    private String _id;

    /**
     * token
     */
    private String name;
}
