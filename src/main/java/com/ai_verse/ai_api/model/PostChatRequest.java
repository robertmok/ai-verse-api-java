package com.ai_verse.ai_api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.ai.ollama.api.OllamaApi;

import java.util.List;

@Setter
@Getter
public class PostChatRequest {
    private List<OllamaApi.Message> history;
    private String model;

    public PostChatRequest(List<OllamaApi.Message> history, String model) {
        this.history = history;
        this.model = model;
    }

}
