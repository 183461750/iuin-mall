package com.iuin.agent.config;

import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author fa
 */
@Configuration
public class McpConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, List<McpSyncClient> mcpSyncClients) {
        MessageWindowChatMemory messageWindowChatMemory = MessageWindowChatMemory.builder().build();
        MessageChatMemoryAdvisor messageChatMemoryAdvisor = MessageChatMemoryAdvisor.builder(messageWindowChatMemory).build();
        SyncMcpToolCallbackProvider syncMcpToolCallbackProvider = new SyncMcpToolCallbackProvider(mcpSyncClients);

        return chatClientBuilder
                .defaultToolCallbacks(syncMcpToolCallbackProvider)
//                .defaultTools(syncMcpToolCallbackProvider)
                .defaultAdvisors(messageChatMemoryAdvisor)
                .build();
    }

}
