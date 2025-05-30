package com.iuin.agent.mcp;

import org.springframework.ai.mcp.server.tool.McpTool;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DatabaseQueryTool {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @McpTool(
        name = "db_query",
        description = "数据库查询",
        parameters = {
            @McpTool.Parameter(name = "sql", type = "string", description = "要执行的SQL语句")
        }
    )
    public Object dbQuery(String sql) {
        try {
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            return "SQL执行异常: " + e.getMessage();
        }
    }
} 