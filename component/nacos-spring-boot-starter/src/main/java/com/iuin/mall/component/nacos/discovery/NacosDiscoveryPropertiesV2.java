package com.iuin.mall.component.nacos.discovery;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * nacos服务发现属性
 *
 * @author fa
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NacosDiscoveryPropertiesV2 extends NacosDiscoveryProperties {

    /**
     * default group name for nacos.
     */
    private String defaultGroup = "DEFAULT_GROUP";

}
