package com.sam.enterpriseai.provider.embedding;

import com.sam.enterpriseai.config.EmbeddingProperties;
import com.sam.enterpriseai.constants.AIProviders;
import com.sam.enterpriseai.dto.ollama.OllamaEmbeddingRequest;
import com.sam.enterpriseai.dto.ollama.OllamaEmbeddingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@ConditionalOnProperty(
        prefix = "ai.embedding",
        name = "provider",
        havingValue = AIProviders.OLLAMA
)
public class OllamaEmbeddingProvider implements EmbeddingProvider {
    private static final Logger log =
            LoggerFactory.getLogger(OllamaEmbeddingProvider.class);

    private static final String EMBED_ENDPOINT = "/api/embed";

    private final RestClient restClient;
    private final EmbeddingProperties embeddingProperties;

    public OllamaEmbeddingProvider(RestClient restClient, EmbeddingProperties embeddingProperties) {
        this.restClient = restClient;
        this.embeddingProperties = embeddingProperties;
    }

    @Override
    public List<Double> generateEmbedding(String text) {

        log.info("Generating embedding using model '{}'.",
                embeddingProperties.getModel());

        OllamaEmbeddingRequest request =
                new OllamaEmbeddingRequest(
                        embeddingProperties.getModel(),
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