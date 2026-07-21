package com.sam.enterpriseai.provider.vectorstore.inmemory.mapper;

import com.sam.enterpriseai.knowledge.embedding.EmbeddedChunk;
import com.sam.enterpriseai.provider.vectorstore.inmemory.model.InMemoryVector;
import org.springframework.stereotype.Component;

@Component
public class InMemoryMapper {

    public InMemoryVector toVector(
            EmbeddedChunk chunk) {

        return new InMemoryVector(
                chunk.getContent(),
                chunk.getEmbedding()
        );
    }

    public EmbeddedChunk toEmbeddedChunk(
            InMemoryVector vector) {

        return new EmbeddedChunk(
                vector.getContent(),
                vector.getEmbedding()
        );
    }
}