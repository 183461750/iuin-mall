package com.iuin.mcp.api.feign;

import com.iuin.common.constants.ModuleConstant;
import com.iuin.common.utils.RespResult;
import com.iuin.mcp.api.fallback.factory.McpTestFeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 测试
 *
 * @author fa
 */
@Primary
@FeignClient(name = ModuleConstant.MCP_SERVICE, fallbackFactory = McpTestFeignFallbackFactory.class)
public interface IMcpTestFeign {

    String PATH_PREFIX = ModuleConstant.MCP_FEIGN_PATH_PREFIX + "/test/";

    /**
     * 根据名称查询id
     */
    @GetMapping(value = PATH_PREFIX + "findIdByName")
    RespResult<String> findIdByName(@RequestParam("name") String name);

}
