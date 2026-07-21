package com.sam.enterpriseai.knowledge.retrieval;

import com.sam.enterpriseai.knowledge.embedding.EmbeddedChunk;
import com.sam.enterpriseai.knowledge.embedding.EmbeddingService;
import com.sam.enterpriseai.provider.vectorstore.VectorStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetrievalServiceTest {

    @Mock
    private EmbeddingService embeddingService;

    @Mock
    private VectorStore vectorStore;

    @InjectMocks
    private RetrievalService retrievalService;

    @Test
    void shouldRetrieveRelevantChunks() {

        String question = "What is Spring Boot?";

        List<Double> embedding =
                List.of(1.0, 0.0);

        EmbeddedChunk embeddedChunk =
                new EmbeddedChunk(
                        "Spring Boot is an opinionated framework.",
                        embedding
                );

        when(embeddingService.embedQuery(question))
                .thenReturn(embedding);

        when(vectorStore.search(embedding, 3))
                .thenReturn(List.of(embeddedChunk));

        List<EmbeddedChunk> result =
                retrievalService.retrieve(question);

        assertEquals(1, result.size());

        assertEquals(
                "Spring Boot is an opinionated framework.",
                result.getFirst().getContent()
        );

        verify(embeddingService)
                .embedQuery(question);

        verify(vectorStore)
                .search(embedding, 3);

        verifyNoMoreInteractions(
                embeddingService,
                vectorStore
        );
    }
}