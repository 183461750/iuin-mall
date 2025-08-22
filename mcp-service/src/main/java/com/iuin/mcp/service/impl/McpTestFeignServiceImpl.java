package com.iuin.mcp.service.impl;

import com.iuin.mcp.service.IMcpTestFeignService;
import org.springframework.stereotype.Service;

/**
 * @author fa
 */
@Service
public class McpTestFeignServiceImpl implements IMcpTestFeignService {

    @Override
    public String findIdByName(String name) {
        return "xxx";
    }

}
