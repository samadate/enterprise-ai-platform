package com.sam.enterpriseai.ai.ingestion;

import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IngestionConfiguration {

    @Bean
    public EmbeddingStoreIngestor embeddingStoreIngestor(
            DocumentSplitter documentSplitter,
            EmbeddingModel embeddingModel,
            EmbeddingStore<TextSegment> embeddingStore
    ) {

        return EmbeddingStoreIngestor.builder()
                .documentSplitter(documentSplitter)
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
    }
}