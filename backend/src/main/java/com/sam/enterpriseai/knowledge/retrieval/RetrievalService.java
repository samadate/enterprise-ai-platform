package com.sam.enterpriseai.knowledge.retrieval;

import com.sam.enterpriseai.knowledge.embedding.EmbeddedChunk;
import com.sam.enterpriseai.knowledge.embedding.EmbeddingService;
import com.sam.enterpriseai.provider.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetrievalService {

    private static final int DEFAULT_TOP_K = 3;

    private final EmbeddingService embeddingService;
    private final VectorStore vectorStore;

    public RetrievalService(
            EmbeddingService embeddingService,
            VectorStore vectorStore) {

        this.embeddingService = embeddingService;
        this.vectorStore = vectorStore;
    }

    public List<EmbeddedChunk> retrieve(String question) {

        List<Double> queryEmbedding =
                embeddingService.embedQuery(question);

        return vectorStore.search(
                queryEmbedding,
                DEFAULT_TOP_K
        );
    }
}