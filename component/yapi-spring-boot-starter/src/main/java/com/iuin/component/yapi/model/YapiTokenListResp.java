package com.iuin.component.yapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class YapiTokenListResp implements Serializable {
    private static final long serialVersionUID = -4848700801924645944L;

    /**
     * 模块名称
     */
    private String name;

    /**
     * token
     */
    private String token;
}
