package com.iuin.agent.task;

import org.springframework.ai.mcp.client.McpSyncClient;
import org.springframework.ai.mcp.schema.CallToolRequest;
import org.springframework.ai.mcp.schema.CallToolResult;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;
import cn.hutool.json.JSONUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

@Component
public class TaskParser {
    @Autowired(required = false)
    private ChatClient deepSeekChatClient;

    public static List<Task> parse(String question) {
        if (question.contains("并行")) {
            return Arrays.asList(new Task("select * from product where id=1"), new Task("select * from product where id=2"));
        } else {
            return Collections.singletonList(new Task("select * from product where id=1"));
        }
    }

    public List<Task> llmParseWithDeepSeek(String question, List<String> memory) {
        if (deepSeekChatClient == null) return parse(question);
        StringBuilder prompt = new StringBuilder();
        prompt.append("你是一个任务拆解专家，请将用户问题拆解为一组可执行的子任务。\n");
        prompt.append("请严格按照如下 JSON Schema 返回结果：\n");
        prompt.append("[\n  {\n    \"desc\": \"string, 子任务自然语言描述，如SQL、API调用、MCP工具调用等\"\n  }\n]\n");
        prompt.append("不要输出多余内容，只返回符合 JSON Schema 的 JSON。\n");
        for (String m : memory) prompt.append(m).append("\n");
        prompt.append("用户问题：").append(question);
        try {
            String result = deepSeekChatClient.prompt(prompt.toString()).call().content();
            return parseJsonArrayToTasks(result);
        } catch (Exception e) {
            // fallback
        }
        return parse(question);
    }

    private List<Task> parseJsonArrayToTasks(String json) {
        List<Task> tasks = new ArrayList<>();
        try {
            JSONArray arr = JSONUtil.parseArray(json);
            for (Object obj : arr) {
                if (obj instanceof JSONObject jo) {
                    String desc = jo.getStr("desc");
                    if (desc != null && !desc.trim().isEmpty()) {
                        tasks.add(new Task(desc.trim()));
                    }
                } else if (obj instanceof Map<?,?> map) {
                    Object desc = map.get("desc");
                    if (desc != null && !desc.toString().trim().isEmpty()) {
                        tasks.add(new Task(desc.toString().trim()));
                    }
                }
            }
        } catch (Exception e) {
            // fallback
        }
        return tasks;
    }

    public static boolean isTaskDone(List<Object> results) {
        return true;
    }
    public static boolean llmIsTaskDone(String question, List<Object> results, List<McpSyncClient> mcpSyncClients) {
        if (mcpSyncClients == null || mcpSyncClients.isEmpty()) return isTaskDone(results); // fallback
        McpSyncClient client = mcpSyncClients.get(0);
        try {
            String prompt = "用户问题：" + question + "\n结果：" + results + "\n请判断任务是否已完成，返回 true/false";
            CallToolRequest req = new CallToolRequest("llm_task_done", Map.of("prompt", prompt));
            CallToolResult result = client.callTool(req);
            Object val = result.result();
            return Boolean.parseBoolean(val.toString());
        } catch (Exception e) {
            // fallback
        }
        return isTaskDone(results);
    }
} 