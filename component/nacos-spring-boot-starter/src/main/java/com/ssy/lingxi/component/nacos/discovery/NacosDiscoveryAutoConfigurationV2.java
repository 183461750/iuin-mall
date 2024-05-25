package com.ssy.lingxi.component.nacos.discovery;

import com.alibaba.cloud.nacos.ConditionalOnNacosDiscoveryEnabled;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.discovery.NacosDiscoveryAutoConfiguration;
import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.iuin.component.base.component.BaseServiceComponent;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动注入自定义的服务发现类
 *
 * @author fa
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
@ConditionalOnNacosDiscoveryEnabled
@AutoConfigureBefore({NacosDiscoveryAutoConfiguration.class})
public class NacosDiscoveryAutoConfigurationV2 {

//    @Bean
//    @ConditionalOnMissingBean
//    public NacosDiscoveryPropertiesV2 nacosProperties() {
//        return new NacosDiscoveryPropertiesV2();
//    }

    @Bean
    @ConditionalOnMissingBean
    public NacosServiceDiscovery nacosServiceDiscovery(NacosDiscoveryProperties nacosDiscoveryProperties, NacosServiceManager nacosServiceManager, BaseServiceComponent baseServiceComponent) {
        return new NacosServiceDiscoveryV2(nacosDiscoveryProperties, nacosServiceManager, baseServiceComponent);
    }
}
