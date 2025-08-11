package com.iuin.mcp.tools;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseQueryTool {

    private final JdbcTemplate jdbcTemplate;

    @Tool(description = "pgsql数据库sql执行工具")
    public String dbQuery(@ToolParam(description = "需要查询的sql") String sql,
                          ToolContext toolContext) {
        try {
            return jdbcTemplate.queryForList(sql).toString();
        } catch (Exception e) {
            return "SQL执行异常: " + e.getMessage();
        }
    }
} 