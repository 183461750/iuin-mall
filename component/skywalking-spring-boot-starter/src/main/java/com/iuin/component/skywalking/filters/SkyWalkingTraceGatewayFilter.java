
package com.iuin.component.skywalking.filters;

import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.apache.skywalking.apm.toolkit.webflux.WebFluxSkyWalkingOperators;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * TraceId过滤器<br />
 * 将SkyWalking的TraceId写入到响应体中
 * @author fa
 */
public class SkyWalkingTraceGatewayFilter implements GlobalFilter {

    String xTraceIdKey = "x-trace-id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String traceId = WebFluxSkyWalkingOperators.continueTracing(exchange, TraceContext::traceId);
        exchange.getResponse().getHeaders().set(xTraceIdKey, traceId);
        return chain.filter(exchange);
    }
}
