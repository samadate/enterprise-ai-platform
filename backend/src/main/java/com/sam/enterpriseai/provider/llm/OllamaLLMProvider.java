package com.sam.enterpriseai.provider.llm;

import com.sam.enterpriseai.config.ChatProperties;
import com.sam.enterpriseai.constants.AIProviders;
import com.sam.enterpriseai.constants.BeanNames;
import com.sam.enterpriseai.constants.LogMessages;
import com.sam.enterpriseai.dto.AIRequest;
import com.sam.enterpriseai.dto.AIResponse;
import com.sam.enterpriseai.dto.ollama.OllamaGenerateRequest;
import com.sam.enterpriseai.dto.ollama.OllamaGenerateResponse;
import com.sam.enterpriseai.exception.AIProviderException;
import com.sam.enterpriseai.mapper.OllamaMapper;
import com.sam.enterpriseai.provider.LLMProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@ConditionalOnProperty(
        prefix = "ai.chat",
        name = "provider",
        havingValue = AIProviders.OLLAMA
)
public class OllamaLLMProvider implements LLMProvider {

    private static final Logger log =
            LoggerFactory.getLogger(OllamaLLMProvider.class);

    private static final String GENERATE_ENDPOINT = "/api/generate";

    private final RestClient restClient;
    private final ChatProperties chatProperties;
    private final OllamaMapper mapper;

    public OllamaLLMProvider(

            @Qualifier(BeanNames.OLLAMA_REST_CLIENT)
            RestClient restClient,

            ChatProperties properties,

            OllamaMapper mapper) {

        this.restClient = restClient;
        this.chatProperties = properties;
        this.mapper = mapper;
    }

    @Override
    public AIResponse generate(AIRequest request) {
        log.info(
                LogMessages.PROVIDER_SELECTED,
                AIProviders.OLLAMA,
                chatProperties.getModel()
        );

        OllamaGenerateRequest ollamaRequest =
                mapper.toGenerateRequest(request);

        try {

            OllamaGenerateResponse response =
                    restClient.post()
                            .uri(GENERATE_ENDPOINT)
                            .body(ollamaRequest)
                            .retrieve()
                            .body(OllamaGenerateResponse.class);

            if (response == null) {
                log.error(LogMessages.PROVIDER_EMPTY_RESPONSE, AIProviders.OLLAMA);

                throw new AIProviderException(
                        AIProviders.OLLAMA,
                        "Failed to communicate with provider."
                );
            }

            log.info(
                    LogMessages.PROVIDER_SUCCESS,
                    AIProviders.OLLAMA
            );

            return mapper.toAIResponse(response);

        } catch (Exception ex) {
            log.error(
                    LogMessages.PROVIDER_FAILURE,
                    AIProviders.OLLAMA,
                    ex
            );

            throw new AIProviderException(
                    AIProviders.OLLAMA,
                    "Failed to communicate with provider.",
                    ex
            );
        }
    }
}