package com.sam.enterpriseai.ai.embedding;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties(EmbeddingProperties.class)
public class EmbeddingProviders {

    @Bean
    @ConditionalOnProperty(
            prefix = "ai.embedding",
            name = "provider",
            havingValue = "ollama"
    )
    public EmbeddingModel ollamaEmbeddingModel(
            EmbeddingProperties properties
    ) {

        return OllamaEmbeddingModel.builder()
                .baseUrl(properties.getBaseUrl())
                .modelName(properties.getModel())
                .timeout(Duration.ofSeconds(150))
                .build();
    }
}