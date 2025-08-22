package com.iuin.agent.service.impl;

import com.iuin.agent.service.ITestService;
import com.iuin.common.utils.RespUtil;
import com.iuin.mcp.api.feign.IMcpTestFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author fa
 */
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements ITestService {

    private final IMcpTestFeign mcpTestFeign;

    @Override
    public String findIdByName(String name) {
        return RespUtil.getDataOrThrow(mcpTestFeign.findIdByName(name));
    }
}
