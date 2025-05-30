package com.iuin.component.ai.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.ai.deepseek.DeepSeekChatClient;
import org.springframework.beans.factory.annotation.Value;

@AutoConfiguration
public class AiAutoConfig {

    @Bean
    public DeepSeekChatClient deepSeekChatClient(@Value("${spring.ai.deepseek.api-key}") String apiKey,
                                                 @Value("${spring.ai.deepseek.base-url:https://api.deepseek.com/v1}") String baseUrl) {
        return new DeepSeekChatClient(apiKey, baseUrl);
    }
} 