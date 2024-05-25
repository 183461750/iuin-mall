package com.iuin.mall.gateway.nacos.test1.nacos.filters;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * https scheme to http
 */

@Component
@Slf4j
public class Https2HttpFilter implements GlobalFilter, Ordered {

    private static final String HTTP = "HTTP";
    private static final int FILTER_ORDER_HTTPS_2_HTTP = 1;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI originalUri = request.getURI();
        ServerHttpRequest.Builder mutate = request.mutate();
        String forwardUri = request.getURI().toString();

        if (forwardUri != null && HttpUtil.isHttps(forwardUri)) {
            try {
                URI mutatedUri = new URI(HTTP,
                        originalUri.getUserInfo(),
                        originalUri.getHost(),
                        originalUri.getPort(),
                        originalUri.getPath(),
                        originalUri.getQuery(),
                        originalUri.getFragment());
                mutate.uri(mutatedUri);
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException("Https related error");
            }
        }
        ServerHttpRequest build = mutate.build();
        return chain.filter(exchange.mutate().request(build).build());
    }

    @Override
    public int getOrder() {
        return FILTER_ORDER_HTTPS_2_HTTP;
    }
}
