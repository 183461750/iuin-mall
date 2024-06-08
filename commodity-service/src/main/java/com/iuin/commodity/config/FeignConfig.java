package com.iuin.commodity.config;

import com.iuin.mall.component.nacos.interceptors.VersionRequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author fa
 */
@Configuration
@Import({
        VersionRequestInterceptor.class,
})
public class FeignConfig {

}
