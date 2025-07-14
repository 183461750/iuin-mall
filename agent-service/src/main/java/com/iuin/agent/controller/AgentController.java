package com.iuin.agent.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/agent")
public class AgentController {

    @Autowired
    private ChatClient chatClient;

    @PostMapping("/ask")
    public String ask(@RequestBody String question) {
        return chatClient.prompt(question).call().content();
    }

    @PostMapping(value = "/sse/ask", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> sseAsk(@RequestBody String question) {
        return chatClient.prompt(question).stream().content();
    }

} 