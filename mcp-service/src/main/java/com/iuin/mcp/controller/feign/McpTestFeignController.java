package com.iuin.mcp.controller.feign;

import com.iuin.common.utils.RespResult;
import com.iuin.mcp.api.feign.IMcpTestFeign;
import com.iuin.mcp.service.IMcpTestFeignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * mcp测试feign接口实现
 *
 * @author fa
 * @ignore 不需要提交到Yapi
 */
@RestController
@RequiredArgsConstructor
public class McpTestFeignController implements IMcpTestFeign {

    private final IMcpTestFeignService mcpTestFeignService;

    /**
     * 根据名称查询id
     */
    @Override
    public RespResult<String> findIdByName(String name) {
        return RespResult.success(mcpTestFeignService.findIdByName(name));
    }

}

