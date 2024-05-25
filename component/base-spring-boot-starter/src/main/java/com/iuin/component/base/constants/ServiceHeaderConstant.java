package com.iuin.component.base.constants;

import lombok.NoArgsConstructor;

/**
 * 服务请求头常量
 *
 * @author fa
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ServiceHeaderConstant {

    /**
     * 商城Id
     */
    public static final String HEADER_SHOP_ID = "Shopid";

    /**
     * nacos版本
     */
    public static final String HEADER_NACOS_VERSION = "Nacos-Version";

    /**
     * nacos分组
     */
    public static final String HEADER_NACOS_GROUP = "Nacos-Group";

}
