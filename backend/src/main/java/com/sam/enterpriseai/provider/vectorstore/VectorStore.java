package com.sam.enterpriseai.provider.vectorstore;

import com.sam.enterpriseai.knowledge.embedding.EmbeddedChunk;

import java.util.List;

public interface VectorStore {

    void saveAll(List<EmbeddedChunk> chunks);

    List<EmbeddedChunk> search(
            List<Double> queryEmbedding,
            int topK
    );

    void clear();
}