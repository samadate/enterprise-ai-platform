package com.sam.enterpriseai.knowledge.ingestion;

import com.sam.enterpriseai.knowledge.chunk.Chunk;
import com.sam.enterpriseai.knowledge.chunk.ChunkingService;
import com.sam.enterpriseai.knowledge.embedding.EmbeddedChunk;
import com.sam.enterpriseai.knowledge.embedding.EmbeddingService;
import com.sam.enterpriseai.provider.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KnowledgeIngestionService {

    private final ChunkingService chunkingService;
    private final EmbeddingService embeddingService;
    private final VectorStore vectorStore;

    public KnowledgeIngestionService(
            ChunkingService chunkingService,
            EmbeddingService embeddingService, VectorStore vectorStore) {

        this.chunkingService = chunkingService;
        this.embeddingService = embeddingService;
        this.vectorStore = vectorStore;
    }

    public List<EmbeddedChunk> ingest(String text) {

        List<Chunk> chunks = chunkingService.chunk(text);

        List<EmbeddedChunk> embeddedChunks = new ArrayList<>();

        for (Chunk chunk : chunks) {
            embeddedChunks.add(
                    embeddingService.embed(chunk)
            );
        }

        vectorStore.saveAll(embeddedChunks);

        return embeddedChunks;
    }
}