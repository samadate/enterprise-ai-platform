package com.sam.enterpriseai.knowledge.embedding;

import com.sam.enterpriseai.knowledge.chunk.Chunk;
import com.sam.enterpriseai.provider.embedding.EmbeddingProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmbeddingService {

    private final EmbeddingProvider embeddingProvider;

    public EmbeddingService(EmbeddingProvider embeddingProvider) {
        this.embeddingProvider = embeddingProvider;
    }

    /**
     * Used during document ingestion.
     */
    public EmbeddedChunk embed(Chunk chunk) {

        List<Double> embedding =
                embeddingProvider.generateEmbedding(chunk.getContent());

        return new EmbeddedChunk(
                chunk.getContent(),
                embedding
        );
    }

    /**
     * Used during retrieval.
     */
    public List<Double> embedQuery(String query) {

        return embeddingProvider.generateEmbedding(query);
    }
}