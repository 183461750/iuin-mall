package com.iuin.mall.gateway.nacos.test2.nacos.config;

import com.ssy.lingxi.gateway.nacos.filters.GrayReactiveLoadBalancerClientFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.gateway.config.GatewayLoadBalancerProperties;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class GrayGatewayReactiveLoadBalancerClientAutoConfiguration {
    public GrayGatewayReactiveLoadBalancerClientAutoConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean({GrayReactiveLoadBalancerClientFilter.class})
    public GrayReactiveLoadBalancerClientFilter grayReactiveLoadBalancerClientFilter(LoadBalancerClientFactory clientFactory, GatewayLoadBalancerProperties properties) {
        log.info("初始化完成....");
        return new GrayReactiveLoadBalancerClientFilter(clientFactory, properties);
    }
}