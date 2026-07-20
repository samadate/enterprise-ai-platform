package com.sam.enterpriseai.provider.embedding;

import com.sam.enterpriseai.config.AIProperties;
import com.sam.enterpriseai.dto.ollama.OllamaEmbeddingRequest;
import com.sam.enterpriseai.dto.ollama.OllamaEmbeddingResponse;
import com.sam.enterpriseai.provider.llm.OllamaLLMProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@ConditionalOnProperty(name = "ai.provider", havingValue = "ollama")
public class OllamaEmbeddingProvider implements EmbeddingProvider {
    private static final Logger log =
            LoggerFactory.getLogger(OllamaEmbeddingProvider.class);

    private static final String EMBED_ENDPOINT = "/api/embed";

    private final RestClient restClient;
    private final AIProperties aiProperties;

    public OllamaEmbeddingProvider(RestClient restClient, AIProperties aiProperties) {
        this.restClient = restClient;
        this.aiProperties = aiProperties;
    }

    @Override
    public List<Double> generateEmbedding(String text) {

        log.info("Generating embedding using model '{}'.",
                aiProperties.getEmbedding().getModel());

        OllamaEmbeddingRequest request =
                new OllamaEmbeddingRequest(
                        aiProperties.getEmbedding().getModel(),
                        text
                );

        OllamaEmbeddingResponse response =
                restClient.post()
                        .uri(EMBED_ENDPOINT)
                        .body(request)
                        .retrieve()
                        .body(OllamaEmbeddingResponse.class);

        if (response == null
                || response.embeddings() == null
                || response.embeddings().isEmpty()) {

            throw new IllegalStateException(
                    "No embedding returned from Ollama."
            );
        }

        return response.embeddings().getFirst();
    }
}