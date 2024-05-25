package com.iuin.mall.gateway.filter;

import com.iuin.common.enums.ResponseCodeEnum;
import com.iuin.common.model.exceptions.BusinessException;
import com.iuin.component.base.utils.IpUtil;
import com.iuin.component.cache.constants.RedisConstant;
import com.iuin.component.cache.component.CacheComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * ip相关拦截器
 *
 * @author Fa
 */
@Order(2)
@Component
@RequiredArgsConstructor
public class IpWhiteListGlobalFilter implements GlobalFilter {
    private final CacheComponent cacheComponent;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取ip地址
        ServerHttpRequest request = exchange.getRequest();
        String ipAddr = IpUtil.getIpAddr(request);

        // 如果在黑名单里面，则禁止访问
        if (cacheComponent.setIsMember(RedisConstant.IP_MONITOR_BLACKLIST, ipAddr)) {
            throw new BusinessException(ResponseCodeEnum.UNAUTHORIZED_REQUEST);
        }

        // 某些请求路径需要白名单才能访问，否则禁止访问（暂时不需要）
        if (needWhiteAuth() && !cacheComponent.setIsMember(RedisConstant.IP_MONITOR_WHITELIST, ipAddr)) {
            throw new BusinessException(ResponseCodeEnum.UNAUTHORIZED_REQUEST);
        }

        return chain.filter(exchange);
    }

    private boolean needWhiteAuth() {
        return Boolean.FALSE;
    }
}
