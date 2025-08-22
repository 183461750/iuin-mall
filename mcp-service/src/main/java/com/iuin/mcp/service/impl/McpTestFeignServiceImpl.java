package com.iuin.mcp.service.impl;

import com.iuin.mcp.service.IMcpTestFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author fa
 */
@Slf4j
@Service
public class McpTestFeignServiceImpl implements IMcpTestFeignService {

    @Override
    public String findIdByName(String name) {
        log.info("feign 中传递的 name 参数值为:[{}]", name);
        return "xxx";
    }

}
