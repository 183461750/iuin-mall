package com.iuin.mall.gateway.filter;

import com.iuin.component.base.enums.IpMonitorTypeEnum;
import com.iuin.common.enums.ResponseCodeEnum;
import com.iuin.component.base.config.IpMonitorConfig;
import com.iuin.component.base.exceptions.BusinessException;
import com.iuin.component.base.utils.IpUtil;
import com.iuin.component.cache.component.FixedWindowLimitingComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局ip流量监控（sentinel是api流量限流，这个是ip流量监控）
 *
 * @author Fa
 */
@Order(1)
@Component
@RequiredArgsConstructor
@Slf4j
public class IpMonitorGlobalFilter implements GlobalFilter {
    private final FixedWindowLimitingComponent fixedWindowLimitingComponent;
    private final IpMonitorConfig ipMonitorConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 如果没开启ip监控则跳过
        if (!ipMonitorConfig.getEnableIpMonitor()) {
            return chain.filter(exchange);
        }

        // 获取ip地址
        ServerHttpRequest request = exchange.getRequest();
        String ipAddr = IpUtil.getIpAddr(request);

        // 生成redisKey
        String key = IpMonitorTypeEnum.generateIpMonitorRedisKey(IpMonitorTypeEnum.SYSTEM, ipAddr);

        // 全局ip流量监控
        if (!fixedWindowLimitingComponent.doLimiting(key, 300, 60)) {
            throw new BusinessException(ResponseCodeEnum.UNAUTHORIZED_REQUEST);
        }

        return chain.filter(exchange);
    }
}
