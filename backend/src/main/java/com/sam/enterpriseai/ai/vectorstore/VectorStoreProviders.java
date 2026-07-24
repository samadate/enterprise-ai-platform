package com.sam.enterpriseai.ai.vectorstore;

import com.sam.enterpriseai.ai.constants.VectorStore;
import com.sam.enterpriseai.ai.embedding.EmbeddingProperties;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VectorStoreProviders {

    private final PgVectorProperties pgVectorProperties;
    private final EmbeddingProperties embeddingProperties;

    public VectorStoreProviders(PgVectorProperties pgVectorProperties, EmbeddingProperties embeddingProperties) {
        this.pgVectorProperties = pgVectorProperties;
        this.embeddingProperties = embeddingProperties;
    }

    @Bean
    @ConditionalOnProperty(
            prefix = "ai.vectorstore",
            name = "provider",
            havingValue = VectorStore.IN_MEMORY
    )
    public EmbeddingStore<TextSegment> embeddingStore() {
        return new InMemoryEmbeddingStore<>();
    }

    @Bean
    @ConditionalOnProperty(
            prefix = "ai.vectorstore",
            name = "provider",
            havingValue = VectorStore.PGVECTOR
    )
    public EmbeddingStore<TextSegment> embeddingStorePgVector() {

        return PgVectorEmbeddingStore.builder()
                .host(pgVectorProperties.getHost())
                .port(pgVectorProperties.getPort())
                .database(pgVectorProperties.getDatabase())
                .user(pgVectorProperties.getUser())
                .password(pgVectorProperties.getPassword())
                .table(pgVectorProperties.getTable())
                .dimension(embeddingProperties.getDimension())
                .build();
    }
}