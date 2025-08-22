package com.iuin.mcp.api.fallback.factory;

import com.ssy.lingxi.order.api.fallback.McpTestFeignFallback;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * mcp测试feign服务降级处理
 *
 * @author fa
 */
@Component
public class McpTestFeignFallbackFactory implements FallbackFactory<McpTestFeignFallback> {

    @Override
    public McpTestFeignFallback create(Throwable cause) {
        return new McpTestFeignFallback(cause);
    }

}
