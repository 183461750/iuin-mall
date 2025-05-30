# ai-spring-boot-starter

Spring Boot Starter for integrating Spring AI (DeepSeek).

## 依赖
- `org.springframework.ai:spring-ai-starter-model-deepseek`
- 需在主工程 application.yml 配置 `spring.ai.deepseek.api-key`

## 快速开始
1. 引入本 starter 依赖
2. 配置 DeepSeek API Key
3. 注入 `DeepSeekChatClient` 使用

```java
@Autowired
private DeepSeekChatClient deepSeekChatClient;

// 使用示例
String result = deepSeekChatClient.prompt("你好").call().content();
``` 