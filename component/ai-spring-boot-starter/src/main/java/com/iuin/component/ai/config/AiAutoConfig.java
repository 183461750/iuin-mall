package com.iuin.component.ai.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.Builder;

@AutoConfiguration
public class AiAutoConfig {

    @Bean
    public ChatClient deepSeekChatClient(Builder chatClientBuilder) {
        // 默认构建，使用 spring-ai deepseek 自动配置的 ChatModel
        return chatClientBuilder.build();
    }
} 