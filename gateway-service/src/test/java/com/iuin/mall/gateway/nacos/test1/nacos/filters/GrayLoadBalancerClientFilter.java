package com.iuin.mall.gateway.nacos.test1.nacos.filters;

import cn.hutool.http.HttpUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultRequest;
import org.springframework.cloud.client.loadbalancer.LoadBalancerUriTools;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.DelegatingServiceInstance;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * 通过GrayLoadBalancer过滤实例
 */
@Component
@Slf4j
public class GrayLoadBalancerClientFilter implements GlobalFilter, Ordered {

    @Resource
    private LoadBalancerClientFactory clientFactory;

    private static final int FILTER_ORDER_GRAY = 10150;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI url = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);

        ServerWebExchangeUtils.addOriginalRequestUrl(exchange, url);
        if (url == null || HttpUtil.isHttp(url.getScheme())) {
            return chain.filter(exchange);
        }
        return doFilter(exchange, chain, url);
    }

    private Mono<Void> doFilter(ServerWebExchange exchange, GatewayFilterChain chain, URI url) {
        return this.choose(exchange).doOnNext(res -> {
            if (!res.hasServer()) {
                throw NotFoundException.create(true, "Unable to find instance for ".concat(url.getHost()));
            }
            URI uri = exchange.getRequest().getURI();
            String overrideScheme = null;
            DelegatingServiceInstance delegatingServiceInstance = new DelegatingServiceInstance(res.getServer(), overrideScheme);
            URI reqUrl = this.reconstructURI(delegatingServiceInstance, uri);
            if (log.isDebugEnabled()) {
                log.debug("GrayLoadBalancerClientFilter url chosen: {}", reqUrl.toString());
            }
            exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, reqUrl);
        }).then(chain.filter(exchange));
    }

    private URI reconstructURI(DelegatingServiceInstance delegatingServiceInstance, URI originalUri) {
        return LoadBalancerUriTools.reconstructURI(delegatingServiceInstance, originalUri);
    }

    private Mono<Response<ServiceInstance>> choose(ServerWebExchange exchange) {
        URI uri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        if (uri == null) {
            throw new RuntimeException(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR + " is null ");
        }
        GrayLoadBalancer loadBalancer = new GrayLoadBalancer(clientFactory.getLazyProvider(uri.getHost(), ServiceInstanceListSupplier.class), uri.getHost());
        return loadBalancer.choose(this.createRequest(exchange));
    }

    private Request createRequest(ServerWebExchange exchange) {
        return new DefaultRequest(exchange.getRequest().getHeaders());
    }


    @Override
    public int getOrder() {
        return FILTER_ORDER_GRAY;
    }
}
