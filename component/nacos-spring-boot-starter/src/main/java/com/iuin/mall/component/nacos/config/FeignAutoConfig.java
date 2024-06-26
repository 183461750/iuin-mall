package com.iuin.mall.component.nacos.config;

import com.iuin.component.base.component.BaseServiceComponent;
import com.iuin.mall.component.nacos.interceptors.VersionRequestInterceptor;
import feign.Client;
import feign.Feign;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author fa
 */
//@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(Feign.class)
@ConditionalOnBean(Client.class)
@AutoConfigureAfter(FeignAutoConfiguration.class)
public class FeignAutoConfig {

    @Bean
    public VersionRequestInterceptor versionRequestInterceptor(BaseServiceComponent baseServiceComponent) {
        return new VersionRequestInterceptor(baseServiceComponent);
    }

}
