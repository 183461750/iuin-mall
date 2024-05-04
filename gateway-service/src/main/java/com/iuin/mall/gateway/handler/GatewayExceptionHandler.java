package com.iuin.mall.gateway.handler;

import cn.hutool.json.JSONUtil;
import com.iuin.common.enums.ResponseCodeEnum;
import com.iuin.common.utils.RespResult;
import com.iuin.component.base.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;


/**
 * 网关全局异常拦截器
 *
 * @author Fa
 */
@Component
@Order(-1)
@Slf4j
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        // 已经commit，则直接返回异常
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        // 根据异常类型，来执行异常处理策略
        RespResult<?> result;
        if (ex instanceof BusinessException) {
            result = businessExceptionHandler(exchange, (BusinessException) ex);
        } else if (ex instanceof ResponseStatusException) {
            result = responseStatusExceptionHandler(exchange, (ResponseStatusException) ex);
        } else {
            result = defaultExceptionHandler(exchange, ex);
        }

        // 返回给前端
        return writeJSON(exchange, result);
    }

    /**
     * 处理业务异常
     */
    public RespResult<?> businessExceptionHandler(ServerWebExchange exchange,
                                                  BusinessException ex) {
        ServerHttpRequest request = exchange.getRequest();
        log.error("[businessException][uri({}/{}) 发生异常]", request.getURI(), request.getMethod(), ex);
        return RespResult.fail(ex.getErrorCode(), ex.getMessage());
    }

    /**
     * Gateway的ResponseStatusException异常
     */
    private RespResult<?> responseStatusExceptionHandler(ServerWebExchange exchange,
                                                         ResponseStatusException ex) {
        //请求的资源不存在
        if (ex.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()))) {
            return RespResult.fail(ResponseCodeEnum.NOT_FOUND);
        }
        //服务暂时不可用
        if (ex.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()))) {
            return RespResult.fail(ResponseCodeEnum.SERVER_UNAVAILABLE);
        }

        ServerHttpRequest request = exchange.getRequest();
        log.error("[responseStatusExceptionHandler][uri({}/{}) 发生异常]", request.getURI(), request.getMethod(), ex);
        return RespResult.fail(ResponseCodeEnum.BUSINESS_ERROR);
    }

    /**
     * 全局默认异常处理
     */
    public RespResult<?> defaultExceptionHandler(ServerWebExchange exchange,
                                                 Throwable ex) {
        ServerHttpRequest request = exchange.getRequest();
        log.error("[defaultExceptionHandler][uri({}/{}) 发生异常]", request.getURI(), request.getMethod(), ex);
        return RespResult.fail(ResponseCodeEnum.BUSINESS_ERROR);
    }

    /**
     * 返回 JSON 字符串
     */
    @SuppressWarnings("deprecation")
    public static Mono<Void> writeJSON(ServerWebExchange exchange, Object object) {
        // 设置 header
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);

        // 设置 body
        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            try {
                return bufferFactory.wrap(JSONUtil.toJsonStr(object).getBytes(StandardCharsets.UTF_8));
            } catch (Exception ex) {
                ServerHttpRequest request = exchange.getRequest();
                log.error("[writeJSON][uri({}/{}) 发生异常]", request.getURI(), request.getMethod(), ex);
                return bufferFactory.wrap(new byte[0]);
            }
        }));
    }
}
