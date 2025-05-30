# agent-service

## 模块定位

本模块为"智能决策 Agent"服务，负责理解用户问题、智能决策、任务拆解与编排，并通过 MCP 协议调用各类 Tool 实现具体能力。

## 架构核心思想

- **Agent（决策者/编排者）**
  - 理解用户问题（可用 LLM，如 DeepSeek）
  - 决策调用哪个 MCP Tool（可基于规则、上下文、或 LLM 推理）
  - 任务拆解、并行/串行编排、多轮记忆
  - 统一收集结果，最终答复用户

- **MCP Tool（能力提供者/执行者）**
  - 只专注于具体功能实现（如查库、外部 API、LLM 问答等）
  - 可用 LLM 也可不用
  - 通过注解式开发，自动注册到 MCP Server

## 主要目录结构

- `controller/AgentController.java`  —— Agent 决策主流程，支持多轮、记忆、智能 Tool 选择
- `task/Task*`                    —— 任务实体、任务拆解与编排逻辑
- `mcp/DatabaseQueryTool.java`    —— 典型 MCP Tool 示例（查库）

## 典型流程

1. 用户提问 → AgentController
2. Agent 拆解任务、决策 Tool、参数准备
3. Agent 通过 MCP 协议调用 Tool
4. Tool 执行（如查库/LLM问答），返回结果
5. Agent 收集结果，必要时多轮，最终答复用户

## 扩展点

- 可在 AgentController 的 `decideTasks`、`decideToolName`、`buildToolParams`、`isTaskDone` 等方法中接入 DeepSeek LLM 或其它推理逻辑，实现更智能的 Agent。
- Tool 层可继续扩展更多能力，Agent 只需决策调用即可。
- 支持多轮记忆、上下文感知、复杂任务拆解与并发。

## 依赖
- Spring Boot 3.x
- spring-ai MCP Server/Client Starter
- spring-ai DeepSeek（可选，推荐用于智能决策/推理）
- PostgreSQL（或其他数据库）

## 启动与配置
- 配置见 `config-service/nacos_config/v1/agent-service.yml`
- 启动后访问 `/agent/ask` POST 接口，传入用户问题 JSON 字符串

## 示例
```json
POST /agent/ask
"帮我查下商品1和商品2的库存，并统计总数"
```

## 进阶
- 支持多种 Tool 动态选择、LLM 参与更深层决策、记忆持久化等。
- 可对接更多外部系统、API、RAG、知识库等。 