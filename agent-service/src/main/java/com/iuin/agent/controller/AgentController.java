package com.iuin.agent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/agent")
public class AgentController {

    private final ChatClient chatClient;

    @PostMapping("/ask")
    public String ask(@RequestBody String question) {
        return chatClient.prompt(question).call().content();
    }

    @PostMapping(value = "/sse/ask", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatClientResponse> sseAsk(@RequestBody String question) {
        return chatClient.prompt(question).stream().chatClientResponse();
    }

    @PostMapping(value = "/sse/ask/v2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatResponse> sseAskV2(@RequestBody String input) {
        Map<String, Object> model = new HashMap<>();
        model.put("input", input);
        PromptTemplate template = new PromptTemplate("{{input}}");
        template.render(model);
        Prompt prompt = new Prompt(template.render());
        return chatClient.prompt(prompt).stream().chatResponse();
    }

} 