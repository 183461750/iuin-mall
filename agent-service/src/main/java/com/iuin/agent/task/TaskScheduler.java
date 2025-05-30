package com.iuin.agent.task;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;
import cn.hutool.json.JSONUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

@Component
public class TaskScheduler {
    @Autowired(required = false)
    private ChatClient deepSeekChatClient;

    public static List<TaskGroup> groupTasks(List<Task> tasks) {
        if (tasks.size() > 1) {
            return Collections.singletonList(new TaskGroup(true, tasks));
        } else {
            return Collections.singletonList(new TaskGroup(false, tasks));
        }
    }

    public List<TaskGroup> llmGroupTasksWithDeepSeek(List<Task> tasks, List<String> memory) {
        if (deepSeekChatClient == null) return groupTasks(tasks);
        StringBuilder prompt = new StringBuilder();
        prompt.append("你是一个任务编排专家，请根据子任务列表判断哪些可以并行，哪些需串行。\n");
        prompt.append("请严格按照如下 JSON Schema 返回结果：\n");
        prompt.append("[\n  {\n    \"parallel\": true, // true表示并行，false表示串行\n    \"tasks\": [\"string, 子任务自然语言描述\"]\n  }\n]\n");
        prompt.append("不要输出多余内容，只返回符合 JSON Schema 的 JSON。\n");
        prompt.append("每个子任务可以是 SQL、API 调用、MCP 工具调用等任意类型。\n");
        for (String m : memory) prompt.append(m).append("\n");
        prompt.append("子任务列表：");
        for (Task t : tasks) prompt.append(t.sql).append("; ");
        try {
            String result = deepSeekChatClient.prompt(prompt.toString()).call().content();
            return parseJsonArrayToTaskGroups(result);
        } catch (Exception e) {
            // fallback
        }
        return groupTasks(tasks);
    }

    private List<TaskGroup> parseJsonArrayToTaskGroups(String json) {
        List<TaskGroup> groups = new ArrayList<>();
        try {
            JSONArray arr = JSONUtil.parseArray(json);
            for (Object obj : arr) {
                if (obj instanceof JSONObject jo) {
                    boolean parallel = jo.getBool("parallel", false);
                    List<Task> groupTasks = new ArrayList<>();
                    JSONArray tasksArr = jo.getJSONArray("tasks");
                    if (tasksArr != null) {
                        for (Object t : tasksArr) {
                            String desc = t != null ? t.toString().trim() : null;
                            if (desc != null && !desc.isEmpty()) {
                                groupTasks.add(new Task(desc));
                            }
                        }
                    }
                    groups.add(new TaskGroup(parallel, groupTasks));
                } else if (obj instanceof Map<?,?> map) {
                    boolean parallel = Boolean.parseBoolean(String.valueOf(map.get("parallel")));
                    List<Task> groupTasks = new ArrayList<>();
                    Object tasksObj = map.get("tasks");
                    if (tasksObj instanceof List<?> list) {
                        for (Object t : list) {
                            String desc = t != null ? t.toString().trim() : null;
                            if (desc != null && !desc.isEmpty()) {
                                groupTasks.add(new Task(desc));
                            }
                        }
                    }
                    groups.add(new TaskGroup(parallel, groupTasks));
                }
            }
        } catch (Exception e) {
            // fallback
        }
        return groups;
    }
} 