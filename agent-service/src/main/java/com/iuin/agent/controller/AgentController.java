package com.iuin.agent.controller;

import com.iuin.agent.task.Task;
import com.iuin.agent.task.TaskGroup;
import com.iuin.agent.task.TaskParser;
import com.iuin.agent.task.TaskScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.mcp.client.McpSyncClient;
import org.springframework.ai.mcp.schema.CallToolRequest;
import org.springframework.ai.mcp.schema.CallToolResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.*;

@RestController
@RequestMapping("/agent")
public class AgentController {
    private static final Logger log = LoggerFactory.getLogger(AgentController.class);

    @Autowired(required = false)
    private List<McpSyncClient> mcpSyncClients;

    @Autowired
    private TaskParser taskParser;

    @Autowired
    private TaskScheduler taskScheduler;

    private final ExecutorService executor = Executors.newCachedThreadPool();

    /**
     * Agent 入口：理解用户问题，决策调用哪个 Tool，编排多轮任务，收集最终结果。
     */
    @PostMapping("/ask")
    public String ask(@RequestBody String question) {
        List<String> memory = new ArrayList<>();
        String currentQuestion = question;
        List<Object> results = new ArrayList<>();
        int maxRounds = 50;
        int round = 0;
        boolean done = false;
        while (round < maxRounds && !done) {
            // 1. Agent 通过 LLM/规则理解用户意图，拆解为子任务
            List<Task> tasks = decideTasks(currentQuestion, memory);
            // 2. Agent 通过 LLM/规则判断任务编排（并行/串行）
            List<TaskGroup> groups = decideTaskGroups(tasks, memory);
            // 3. Agent 决策每个子任务应调用哪个 Tool，并准备参数
            List<Future<Object>> futures = new ArrayList<>();
            results.clear();
            for (TaskGroup group : groups) {
                if (group.parallel) {
                    for (Task t : group.tasks) {
                        futures.add(executor.submit(() -> callTool(decideToolName(t, memory), buildToolParams(t, memory))));
                    }
                    for (Future<Object> f : futures) {
                        try { results.add(f.get()); } catch (Exception e) { results.add(e.getMessage()); }
                    }
                    futures.clear();
                } else {
                    for (Task t : group.tasks) {
                        results.add(callTool(decideToolName(t, memory), buildToolParams(t, memory)));
                    }
                }
            }
            // 4. Agent 记忆本轮问答
            memory.add("Q:" + currentQuestion);
            memory.add("A:" + results);
            // 5. Agent 判断任务是否完成
            done = isTaskDone(currentQuestion, results, memory);
            if (!done) {
                currentQuestion = buildNextQuestion(question, memory, results, round);
            }
            round++;
        }
        if (done) {
            return "任务完成，结果：" + results;
        } else {
            log.warn("[Agent任务未完成] 轮次:{}\n历史对话:{}\n最后一轮结果:{}", round, memory, results);
            return "任务未完成，请稍后重试。";
        }
    }

    /**
     * Agent 决策：根据当前问题和记忆，拆解为子任务（可用 LLM 或规则）
     */
    private List<Task> decideTasks(String question, List<String> memory) {
        // 使用 deepseek LLM 解析
        return taskParser.llmParseWithDeepSeek(question, memory);
    }

    /**
     * Agent 决策：根据子任务和记忆，判断并行/串行分组（可用 LLM 或规则）
     */
    private List<TaskGroup> decideTaskGroups(List<Task> tasks, List<String> memory) {
        // 使用 deepseek LLM 判断并行/串行
        return taskScheduler.llmGroupTasksWithDeepSeek(tasks, memory);
    }

    /**
     * Agent 决策：为每个子任务选择合适的 Tool（通过 LLM + 动态工具列表）
     */
    private String decideToolName(Task task, List<String> memory) {
        // 1. 获取所有MCP工具详细信息
        List<String> toolInfos = new ArrayList<>();
        if (mcpSyncClients != null && !mcpSyncClients.isEmpty()) {
            try {
                // 依赖 spring-ai-mcp-client 1.0.0+，需引入McpToolUtils
                var toolCallbacks = org.springframework.ai.mcp.util.McpToolUtils.getToolCallbacksFromSyncClients(mcpSyncClients);
                for (var tc : toolCallbacks) {
                    var def = tc.getToolDefinition();
                    StringBuilder sb = new StringBuilder();
                    sb.append("工具名: ").append(def.getName()).append("\n");
                    sb.append("描述: ").append(def.getDescription()).append("\n");
                    sb.append("参数: ");
                    if (def.getParameters() != null && !def.getParameters().isEmpty()) {
                        def.getParameters().forEach((k, v) -> sb.append(k).append(": ").append(v).append(", "));
                    } else {
                        sb.append("无");
                    }
                    toolInfos.add(sb.toString());
                }
            } catch (Exception e) {
                log.warn("获取MCP工具信息失败: {}", e.getMessage());
            }
        }
        // 2. 将工具信息放入memory，供LLM推理
        memory.add("可用工具列表:\n" + String.join("\n\n", toolInfos));
        // 3. 通过LLM决策选择合适的tool（调用deepseek LLM）
        // 这里假设有 deepSeekChatClient，可用 prompt 让LLM选择工具名
        String toolName = "db_query"; // fallback
        try {
            if (taskParser != null && taskParser.deepSeekChatClient != null) {
                StringBuilder prompt = new StringBuilder();
                prompt.append("你是一个智能Agent，请根据子任务描述和可用工具列表，选择最合适的工具名称（只返回工具名，不要多余内容）。\n");
                prompt.append("子任务描述: ").append(task.sql).append("\n");
                prompt.append("可用工具列表:\n");
                for (String info : toolInfos) prompt.append(info).append("\n\n");
                String result = taskParser.deepSeekChatClient.prompt(prompt.toString()).call().content();
                if (result != null && !result.trim().isEmpty()) {
                    toolName = result.trim();
                }
            }
        } catch (Exception e) {
            log.warn("LLM工具决策异常: {}", e.getMessage());
        }
        return toolName;
    }

    /**
     * Agent 决策：为 Tool 构造参数（可用 LLM 或规则）
     */
    private Map<String, Object> buildToolParams(Task task, List<String> memory) {
        // 可扩展为 LLM 生成参数，当前简单规则
        Map<String, Object> params = new HashMap<>();
        params.put("sql", task.sql);
        return params;
    }

    /**
     * Agent 统一调用 MCP Tool
     */
    private Object callTool(String toolName, Map<String, Object> params) {
        if (mcpSyncClients == null || mcpSyncClients.isEmpty()) return "MCP Client 未配置";
        McpSyncClient client = mcpSyncClients.get(0);
        try {
            CallToolRequest req = new CallToolRequest(toolName, params);
            CallToolResult result = client.callTool(req);
            return result.result();
        } catch (Exception e) {
            log.error("MCP Tool 调用异常", e);
            return "MCP Tool 调用异常: " + e.getMessage();
        }
    }

    /**
     * Agent 判断任务是否完成（可用 LLM 或规则）
     */
    private boolean isTaskDone(String question, List<Object> results, List<String> memory) {
        // 可扩展为 LLM 判断
        return TaskParser.llmIsTaskDone(question, results, mcpSyncClients);
    }

    /**
     * Agent 生成下一轮问题，带上下文记忆（可用 LLM 或规则）
     */
    private String buildNextQuestion(String original, List<String> memory, List<Object> lastResults, int round) {
        StringBuilder sb = new StringBuilder();
        sb.append("基于以下历史对话和结果，继续完成用户最初的问题：");
        for (String m : memory) sb.append("\n").append(m);
        sb.append("\n用户最初问题：").append(original);
        sb.append("\n请继续拆解并完成剩余任务。");
        return sb.toString();
    }
} 