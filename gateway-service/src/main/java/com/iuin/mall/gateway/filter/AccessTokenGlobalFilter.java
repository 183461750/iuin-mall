package com.iuin.mall.gateway.filter;

import com.iuin.common.constants.CommonConstant;
import com.iuin.common.enums.ResponseCodeEnum;
import com.iuin.common.model.exceptions.BusinessException;
import com.iuin.component.cache.component.CacheComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Stream;

/**
 * AccessToken相关拦截器
 *
 * @author Fa
 */
@Order(3)
@Component
@RequiredArgsConstructor
public class AccessTokenGlobalFilter implements GlobalFilter {
    private final CacheComponent cacheComponent;

    /**
     * 指定不需要进行Token和Auth验证的Url
     */
    private static final List<String> excludeUrlList = Stream.of(
            "/login", "/oauth/token", "/member/register", "/member/login"
    ).toList();

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, GatewayFilterChain gatewayFilterChain) {
        //请求路径
        String requestPath = serverWebExchange.getRequest().getPath().value();
        HttpHeaders headers = serverWebExchange.getRequest().getHeaders();
        String accessToken = headers.getFirst(CommonConstant.ACCESS_TOKEN);

        //过滤不需要验证的Url
        //TODO 当前版不做校验，后续版本再加
        if (excludeUrlList.contains(requestPath) || Boolean.TRUE) {
            return gatewayFilterChain.filter(serverWebExchange);
        }

        // 验证是否登录
        if (!StringUtils.hasLength(accessToken) || !cacheComponent.hasKey(accessToken)) {
            throw new BusinessException(ResponseCodeEnum.TOKEN_EXPIRE);
        }

        return gatewayFilterChain.filter(serverWebExchange);
    }
}
