package com.ssy.lingxi.order.api.fallback;

import com.iuin.common.enums.ResponseCodeEnum;
import com.iuin.common.utils.RespResult;
import com.iuin.mcp.api.feign.IMcpTestFeign;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述: mcp测试服务熔断处理
 *
 * @author fa
 */
@Slf4j
public class McpTestFeignFallback implements IMcpTestFeign {

    private final Throwable throwable;

    public McpTestFeignFallback(Throwable cause) {
        this.throwable = cause;
    }

    @Override
    public RespResult<String> findIdByName(String name) {
        log.error(throwable.getMessage());
        return RespResult.fail(ResponseCodeEnum.MCP_SERVER_ERROR);
    }

}
