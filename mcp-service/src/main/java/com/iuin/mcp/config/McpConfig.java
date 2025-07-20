package com.iuin.mcp.config;

import com.iuin.mcp.tools.DatabaseQueryTool;
import com.iuin.mcp.tools.WeatherService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fa
 */
@Configuration
public class McpConfig {

    @Bean
    public ToolCallbackProvider tools(WeatherService weatherService, DatabaseQueryTool databaseQueryTool) {
        return MethodToolCallbackProvider.builder().toolObjects(weatherService).build();
    }

}
