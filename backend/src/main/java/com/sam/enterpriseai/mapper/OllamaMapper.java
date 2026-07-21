package com.sam.enterpriseai.mapper;

import com.sam.enterpriseai.config.ChatProperties;
import com.sam.enterpriseai.dto.AIRequest;
import com.sam.enterpriseai.dto.AIResponse;
import com.sam.enterpriseai.dto.ollama.OllamaGenerateRequest;
import com.sam.enterpriseai.dto.ollama.OllamaGenerateResponse;
import org.springframework.stereotype.Component;

@Component
public class OllamaMapper {

    private final ChatProperties chatProperties;

    public OllamaMapper(ChatProperties chatProperties) {
        this.chatProperties = chatProperties;
    }

    public OllamaGenerateRequest toGenerateRequest(AIRequest request) {

        return new OllamaGenerateRequest(
                chatProperties.getModel(),
                request.prompt(),
                false
        );
    }

    public AIResponse toAIResponse(OllamaGenerateResponse response) {

        return new AIResponse(
                response.response()
        );
    }
}