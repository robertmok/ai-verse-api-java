package com.ai_verse.ai_api.controller;

import com.ai_verse.ai_api.model.PostChatRequest;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@RestController
@RequestMapping("/api/Ai")
@CrossOrigin(origins = ("http://localhost:4200"))
public class AiVerseController {

    private final OllamaApi ollamaApi;
    private final Sinks.Many<ServerSentEvent<OllamaApi.ChatResponse>> sink;

    public AiVerseController(OllamaApi ollamaApi) {
        this.ollamaApi = ollamaApi;
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @PostMapping(value = "/postStreamChat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<OllamaApi.ChatResponse>> postOllamaStreamChat(@RequestBody PostChatRequest postChatRequest) {
        var request = OllamaApi.ChatRequest.builder(postChatRequest.getModel())
                .withStream(true)
                .withMessages(postChatRequest.getHistory())
                .build();

        return ollamaApi.streamingChat(request)
                .map(response -> ServerSentEvent.builder(response).build()) // Transform ChatResponse to ServerSentEvent
                .doOnNext(sink::tryEmitNext); // Emit ServerSentEvent into sink
    }

    @GetMapping(value = "/streamChatEvents", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<OllamaApi.ChatResponse>> streamChatEvents() {
        return sink.asFlux();
    }
}
