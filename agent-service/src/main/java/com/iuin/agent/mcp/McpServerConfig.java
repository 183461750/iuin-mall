package com.iuin.agent.mcp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.mcp.server.tool.McpToolHandler;

@Configuration
public class McpServerConfig {
    @Bean
    public McpToolHandler dbQueryTool(DatabaseQueryTool tool) {
        return tool;
    }
} 