package com.iuin.agent;

import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.Builder;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import java.util.List;

@SpringBootApplication
public class AgentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AgentServiceApplication.class, args);
    }

    @Bean
    public ChatClient chatClient(Builder chatClientBuilder, List<McpSyncClient> mcpSyncClients) {
        return chatClientBuilder
            .defaultToolCallbacks(new SyncMcpToolCallbackProvider(mcpSyncClients))
            .defaultAdvisors(MessageChatMemoryAdvisor.builder(MessageWindowChatMemory.builder().build()).build())
            .build();
    }
} 