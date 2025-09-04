package com.iuin.mall.component.nacos.config;

import com.iuin.mall.component.nacos.loadbalancer.GrayLabelServiceInstanceListSupplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 此处选择全局配置
 *
 * @author Fa
 */
@Slf4j
@LoadBalancerClients(defaultConfiguration = GrayLabelServiceInstanceListSupplierConfiguration.class)
public class GrayLabelServiceInstanceListSupplierConfiguration {

    /**
     * @see LoadBalancerClientConfiguration.BlockingSupportConfiguration#discoveryClientServiceInstanceListSupplier(ConfigurableApplicationContext)
     */
    @Bean
    @ConditionalOnClass(name = "org.springframework.web.servlet.DispatcherServlet")
    @ConditionalOnProperty(value = "spring.main.web-application-type", havingValue = "servlet", matchIfMissing = true)
    public GrayLabelServiceInstanceListSupplier servlet(
            ConfigurableApplicationContext context) {
        ServiceInstanceListSupplier delegate = ServiceInstanceListSupplier.builder().withBlockingDiscoveryClient()
                .withCaching().build(context);
        return new GrayLabelServiceInstanceListSupplier(delegate);
    }

    /**
     * @see LoadBalancerClientConfiguration.ReactiveSupportConfiguration#discoveryClientServiceInstanceListSupplier(ConfigurableApplicationContext)
     */
    @Bean
    @ConditionalOnClass(name = "org.springframework.web.reactive.DispatcherHandler")
    @ConditionalOnProperty(value = "spring.main.web-application-type", havingValue = "reactive")
    public GrayLabelServiceInstanceListSupplier reactive(
            ConfigurableApplicationContext context) {
        ServiceInstanceListSupplier delegate = ServiceInstanceListSupplier.builder().withDiscoveryClient()
                .withCaching().build(context);
        return new GrayLabelServiceInstanceListSupplier(delegate);
    }

}

