package com.sam.enterpriseai.ai.retrieval;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import dev.langchain4j.rag.query.Query;

import java.util.List;

@SpringBootTest
class ContentRetrieverIT {

    @Autowired
    private EmbeddingStoreIngestor embeddingStoreIngestor;

    @Autowired
    private ContentRetriever contentRetriever;

    @Test
    void shouldRetrieveRelevantContent() {

        Document document = Document.from("""
                Artificial Intelligence is transforming enterprises.

                Large Language Models are becoming the foundation
                of modern enterprise applications.

                Retrieval Augmented Generation improves factual accuracy.
                """);

        embeddingStoreIngestor.ingest(document);

        Query query = Query.from("What improves factual accuracy?");

        List<Content> contents =
                contentRetriever.retrieve(query);

        System.out.println("--------------------------------");

        System.out.println("Retrieved Chunks : " + contents.size());

        contents.forEach(content -> {
            System.out.println();
            System.out.println(content.textSegment().text());
        });

        System.out.println("--------------------------------");
    }
}