package com.sam.enterpriseai.ai.ingestion;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.IngestionResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.model.embedding.EmbeddingModel;

@SpringBootTest
class EmbeddingStoreIngestorIT {

    @Autowired
    private EmbeddingStoreIngestor embeddingStoreIngestor;

    @Autowired
    private EmbeddingStore<TextSegment> embeddingStore;

    @Autowired
    private EmbeddingModel embeddingModel;

    @Test
    void shouldIngestDocument() {

        Document document = Document.from("""
                Artificial Intelligence is transforming enterprises.

                Large Language Models are becoming the foundation
                of modern enterprise applications.

                Retrieval Augmented Generation improves factual accuracy.
                """);

        IngestionResult ingestionResult = embeddingStoreIngestor.ingest(document);

        Embedding queryEmbedding =
                embeddingModel.embed("What improves factual accuracy?")
                        .content();

        EmbeddingSearchResult<TextSegment> result =
                embeddingStore.search(
                        EmbeddingSearchRequest.builder()
                                .queryEmbedding(queryEmbedding)
                                .maxResults(3)
                                .build()
                );

        System.out.println("--------------------------------");
        System.out.println("Matches : " + result.matches().size());

        for (EmbeddingMatch<TextSegment> match : result.matches()) {
            System.out.println();
            System.out.println("Score : " + match.score());
            System.out.println(match.embedded().text());
        }
        System.out.println("--------------------------------");

    }
}