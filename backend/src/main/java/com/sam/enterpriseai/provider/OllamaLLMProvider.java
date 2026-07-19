package com.sam.enterpriseai.provider;

import com.sam.enterpriseai.config.AIProperties;
import com.sam.enterpriseai.constants.AIProviders;
import com.sam.enterpriseai.constants.BeanNames;
import com.sam.enterpriseai.dto.AIRequest;
import com.sam.enterpriseai.dto.AIResponse;
import com.sam.enterpriseai.dto.ollama.OllamaGenerateRequest;
import com.sam.enterpriseai.dto.ollama.OllamaGenerateResponse;
import com.sam.enterpriseai.mapper.OllamaMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@ConditionalOnProperty(
        prefix = "ai",
        name = "provider",
        havingValue = AIProviders.OLLAMA
)
public class OllamaLLMProvider implements LLMProvider {

    private final RestClient restClient;
    private final AIProperties aiProperties;
    private final OllamaMapper mapper;

    public OllamaLLMProvider(

            @Qualifier(BeanNames.OLLAMA_REST_CLIENT)
            RestClient restClient,

            AIProperties properties,

            OllamaMapper mapper) {

        this.restClient = restClient;
        this.aiProperties = properties;
        this.mapper = mapper;
    }

    @Override
    public AIResponse generate(AIRequest request) {

        OllamaGenerateRequest ollamaRequest =
                mapper.toGenerateRequest(request);

        OllamaGenerateResponse response =
                restClient.post()
                        .uri(
                                aiProperties
                                        .getOllama()
                                        .getGenerateEndpoint()
                        )
                        .body(ollamaRequest)
                        .retrieve()
                        .body(OllamaGenerateResponse.class);
        if (response == null) {
            throw new IllegalStateException("Received null response from Ollama.");
        }

        return mapper.toAIResponse(response);
    }
}