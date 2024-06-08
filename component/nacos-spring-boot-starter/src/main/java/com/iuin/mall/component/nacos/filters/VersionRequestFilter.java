package com.iuin.mall.component.nacos.filters;

import cn.hutool.json.JSONUtil;
import com.iuin.component.base.constants.ServiceHeaderConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关中的灰度发布全局过滤器
 *
 * @author Administrator
 */
@Slf4j
//@Component
@ConditionalOnClass(name = {
        "org.springframework.web.reactive.DispatcherHandler",
        "org.springframework.cloud.gateway.filter.GlobalFilter"
})
@ConditionalOnProperty(value = "spring.main.web-application-type", havingValue = "reactive")
public class VersionRequestFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("进入到了灰度的filter上");

        // ① 解析请求头
        HttpHeaders headers = exchange.getRequest().getHeaders();
        // ③ 将灰度标记放入请求头中
        ServerHttpRequest tokenRequest = exchange.getRequest().mutate()
                // 将灰度标记传递过去
                .header(ServiceHeaderConstant.HEADER_NACOS_VERSION, headers.getFirst(ServiceHeaderConstant.HEADER_NACOS_VERSION))
                .build();

        HttpHeaders headers2 = tokenRequest.getHeaders();

        log.info("网关灰度设置之后，获取到的request是：{}", JSONUtil.toJsonStr(headers2));

        ServerWebExchange build = exchange.mutate().request(tokenRequest).build();
        return chain.filter(build);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}