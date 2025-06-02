package com.iuin.agent.mcp;

import org.springframework.ai.tool.Tool;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DatabaseQueryTool {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Tool(description = "数据库查询，传入SQL语句，返回结果列表")
    public Object dbQuery(String sql) {
        try {
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            return "SQL执行异常: " + e.getMessage();
        }
    }
} 