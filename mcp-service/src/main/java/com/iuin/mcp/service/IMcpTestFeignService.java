package com.iuin.mcp.service;

/**
 * @author fa
 */
public interface IMcpTestFeignService {
    /**
     * 根据名称查询id
     */
    String findIdByName(String name);
}
