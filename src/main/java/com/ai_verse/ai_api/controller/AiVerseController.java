package com.ai_verse.ai_api.controller;

import com.ai_verse.ai_api.model.PostChatRequest;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/Ai")
public class AiVerseController {

    private final OllamaApi ollamaApi;

    public AiVerseController(OllamaApi ollamaApi) {
        this.ollamaApi = ollamaApi;
    }

    @PostMapping("/postStreamChat")
    public Flux<OllamaApi.ChatResponse> postOllamaStreamChatAsync(@RequestBody PostChatRequest postChatRequest) {
        var request = OllamaApi.ChatRequest.builder(postChatRequest.getModel())
                .withStream(true)
                .withMessages(postChatRequest.getHistory())
                .build();

        return ollamaApi.streamingChat(request);
    }

}
