package com.sam.enterpriseai.knowledge.embedding;

import com.sam.enterpriseai.knowledge.chunk.Chunk;
import com.sam.enterpriseai.provider.embedding.EmbeddingProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class EmbeddingServiceTest {

    private EmbeddingProvider embeddingProvider;
    private EmbeddingService embeddingService;

    @BeforeEach
    void setUp() {
        embeddingProvider = mock(EmbeddingProvider.class);
        embeddingService = new EmbeddingService(embeddingProvider);
    }

    @Test
    void shouldGenerateEmbeddedChunk() {

        // Arrange
        Chunk chunk = new Chunk("Spring Boot is awesome.");

        List<Double> embedding =
                List.of(0.12, 0.34, 0.56);

        when(embeddingProvider.generateEmbedding(chunk.getContent()))
                .thenReturn(embedding);

        // Act
        EmbeddedChunk embeddedChunk = embeddingService.embed(chunk);

        // Assert
        assertThat(embeddedChunk).isNotNull();

        assertThat(embeddedChunk.getContent())
                .isEqualTo(chunk.getContent());

        assertThat(embeddedChunk.getEmbedding())
                .isEqualTo(embedding);

        verify(embeddingProvider, times(1))
                .generateEmbedding(chunk.getContent());

        verifyNoMoreInteractions(embeddingProvider);
    }

    @Test
    void shouldReturnEmptyEmbeddingWhenProviderReturnsEmptyList() {

        // Arrange
        Chunk chunk = new Chunk("Test");

        when(embeddingProvider.generateEmbedding(chunk.getContent()))
                .thenReturn(List.of());

        // Act
        EmbeddedChunk embeddedChunk = embeddingService.embed(chunk);

        // Assert
        assertThat(embeddedChunk).isNotNull();

        assertThat(embeddedChunk.getContent())
                .isEqualTo("Test");

        assertThat(embeddedChunk.getEmbedding())
                .isEmpty();

        verify(embeddingProvider).generateEmbedding("Test");
        verifyNoMoreInteractions(embeddingProvider);
    }

    @Test
    void shouldPropagateExceptionWhenProviderFails() {

        // Arrange
        Chunk chunk = new Chunk("Test");

        RuntimeException exception =
                new RuntimeException("Embedding generation failed.");

        when(embeddingProvider.generateEmbedding(chunk.getContent()))
                .thenThrow(exception);

        // Act & Assert
        org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> embeddingService.embed(chunk)
        );

        verify(embeddingProvider).generateEmbedding(chunk.getContent());
        verifyNoMoreInteractions(embeddingProvider);
    }
}