package com.sam.enterpriseai.mapper;

import com.sam.enterpriseai.config.AIProperties;
import com.sam.enterpriseai.dto.AIRequest;
import com.sam.enterpriseai.dto.AIResponse;
import com.sam.enterpriseai.dto.ollama.OllamaGenerateRequest;
import com.sam.enterpriseai.dto.ollama.OllamaGenerateResponse;
import org.springframework.stereotype.Component;

@Component
public class OllamaMapper {

    private final AIProperties aiProperties;

    public OllamaMapper(AIProperties aiProperties) {
        this.aiProperties = aiProperties;
    }

    public OllamaGenerateRequest toGenerateRequest(AIRequest request) {

        return new OllamaGenerateRequest(
                aiProperties.getChat().getModel(),
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