package com.sam.enterpriseai.ai.service;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EnterpriseAssistantIT {

    @Autowired
    private EnterpriseAssistant enterpriseAssistant;

    @Autowired
    private EmbeddingStoreIngestor embeddingStoreIngestor;

    @Test
    void shouldAnswerUsingRag() {

        Document document = Document.from("""
                Employee badge number 99999 belongs to Project Phoenix.
                Project Phoenix launch date is 14 August 2027.""");

        embeddingStoreIngestor.ingest(document);

        String answer = enterpriseAssistant.chat(
                "I want only year in which Project Phoenix launched?"
        );

        System.out.println("--------------------------------");
        System.out.println(answer);
        System.out.println("--------------------------------");
    }
}