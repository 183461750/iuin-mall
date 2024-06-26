package com.iuin.mall.gateway.nacos.test2.nacos.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultRequest;
import org.springframework.cloud.client.loadbalancer.LoadBalancerUriTools;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.gateway.config.GatewayLoadBalancerProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.cloud.gateway.support.DelegatingServiceInstance;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @ClassName: GrayReactiveLoadBalancerClientFilter
 * @Description TODO
 * @author 月夜烛峰
 * @date 2022/10/8 15:40
 */
@Slf4j
public class GrayReactiveLoadBalancerClientFilter implements GlobalFilter, Ordered {
    /**读取网关请求报文体缓存key*/
    public static final String CACHE_REQUEST_BODY_OBJECT_KEY = "cachedRequestBodyObject";
 
    private static final int LOAD_BALANCER_CLIENT_FILTER_ORDER = 10150;
    private final LoadBalancerClientFactory clientFactory;
    private final GatewayLoadBalancerProperties properties;
 
    public GrayReactiveLoadBalancerClientFilter(LoadBalancerClientFactory clientFactory, GatewayLoadBalancerProperties properties) {
        this.clientFactory = clientFactory;
        this.properties = properties;
    }
 
    @Override
    public int getOrder() {
        return LOAD_BALANCER_CLIENT_FILTER_ORDER;
    }
 
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI url = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        Object cachedBody = exchange.getAttribute(CACHE_REQUEST_BODY_OBJECT_KEY);
        log.info("请求报文体：{}",cachedBody);
        String schemePrefix = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_SCHEME_PREFIX_ATTR);
        if (url != null && ("grayLb".equals(url.getScheme()) || "grayLb".equals(schemePrefix)|| "lb".equals(url.getScheme()))) {
            ServerWebExchangeUtils.addOriginalRequestUrl(exchange, url);
            log.info("负载均衡选择：{}", ReactiveLoadBalancerClientFilter.class.getSimpleName() + " url before: " + url);
 
            return this.choose(exchange).doOnNext(response -> {
                if (!response.hasServer()) {
                    throw NotFoundException.create(this.properties.isUse404() , "Unable to find instance for " + url.getHost());
                } else {
                    URI uri = exchange.getRequest().getURI();
                    String overrideScheme = null;
                    if (schemePrefix != null) {
                        overrideScheme = url.getScheme();
                    }
 
                    DelegatingServiceInstance serviceInstance = new DelegatingServiceInstance(response.getServer(), overrideScheme);
                    URI requestUrl = this.reconstructURI(serviceInstance, uri);
                    log.info("LoadBalancerClientFilter url chosen: {}" , requestUrl);
 
                    exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, requestUrl);
                }
            }).then(chain.filter(exchange));
        } else {
            return chain.filter(exchange);
        }
    }
 
    protected URI reconstructURI(ServiceInstance serviceInstance, URI original) {
        return LoadBalancerUriTools.reconstructURI(serviceInstance, original);
    }
 
    private Mono<Response<ServiceInstance>> choose(ServerWebExchange exchange) {
        URI uri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        GrayLoadBalancer loadBalancer = new GrayLoadBalancer(clientFactory.getLazyProvider(uri.getHost(), ServiceInstanceListSupplier.class), uri.getHost());
        if (loadBalancer == null) {
            throw new NotFoundException("No loadbalancer available for " + uri.getHost());
        } else {
            return loadBalancer.choose(this.createRequest(exchange));
        }
    }
 
    private Request createRequest(ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        return new DefaultRequest<>(headers);
    }
}