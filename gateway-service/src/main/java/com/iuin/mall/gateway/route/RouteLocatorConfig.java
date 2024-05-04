package com.iuin.mall.gateway.route;

import com.iuin.common.constants.ModuleConstant;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 路由配置器
 *
 * @author Fa
 */
@Configuration
public class RouteLocatorConfig {
    private static final String PATH_SUFFIX = "/**";
    private static final String URI_PREFIX = "lb://";

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // 商品
                .route(ModuleConstant.COMMODITY_SERVICE,
                        r -> r.path(ModuleConstant.COMMODITY_PATH_PREFIX + PATH_SUFFIX)
                                .uri(URI_PREFIX + ModuleConstant.COMMODITY_SERVICE))
                // 搜索
                .route(ModuleConstant.SEARCH_SERVICE,
                        r -> r.path(ModuleConstant.SEARCH_PATH_PREFIX + PATH_SUFFIX)
                                .uri(URI_PREFIX + ModuleConstant.SEARCH_SERVICE))
                .build();
    }
}
