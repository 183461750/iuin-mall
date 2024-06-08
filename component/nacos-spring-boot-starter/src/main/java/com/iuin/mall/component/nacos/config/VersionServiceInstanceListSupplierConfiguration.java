package com.iuin.mall.component.nacos.config;

import com.iuin.mall.component.nacos.loadbalancer.VersionServiceInstanceListSupplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 此处选择全局配置
 *
 * @author huan.fu
 * @since 2023/6/19 - 22:16
 */
@Slf4j
@LoadBalancerClients(defaultConfiguration = VersionServiceInstanceListSupplierConfiguration.class)
public class VersionServiceInstanceListSupplierConfiguration {

    @Bean
    @ConditionalOnClass(name = "org.springframework.web.servlet.DispatcherServlet")
    @ConditionalOnProperty(value = "spring.main.web-application-type", havingValue = "servlet", matchIfMissing = true)
    public VersionServiceInstanceListSupplier versionServiceInstanceListSupplierV1(
            ConfigurableApplicationContext context) {
        log.info("===========> versionServiceInstanceListSupplierV1");
        ServiceInstanceListSupplier delegate = ServiceInstanceListSupplier.builder()
                .withBlockingDiscoveryClient()
                .withCaching()
                .build(context);
        return new VersionServiceInstanceListSupplier(delegate);
    }

    @Bean
    @ConditionalOnClass(name = "org.springframework.web.reactive.DispatcherHandler")
    @ConditionalOnProperty(value = "spring.main.web-application-type", havingValue = "reactive")
    public VersionServiceInstanceListSupplier versionServiceInstanceListSupplierV2(
            ConfigurableApplicationContext context) {
        log.info("===========> versionServiceInstanceListSupplierV2");
        ServiceInstanceListSupplier delegate = ServiceInstanceListSupplier.builder()
                .withDiscoveryClient()
                .withCaching()
                .build(context);
        return new VersionServiceInstanceListSupplier(delegate);
    }
}

