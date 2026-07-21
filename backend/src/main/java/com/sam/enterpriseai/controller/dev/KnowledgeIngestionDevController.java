package com.sam.enterpriseai.controller.dev;

import com.sam.enterpriseai.dto.IngestionRequest;
import com.sam.enterpriseai.knowledge.embedding.EmbeddedChunk;
import com.sam.enterpriseai.knowledge.ingestion.KnowledgeIngestionService;
import com.sam.enterpriseai.provider.vectorstore.inmemory.InMemoryVectorStore;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dev/ingestion")
public class KnowledgeIngestionDevController {

    private final KnowledgeIngestionService ingestionService;
    private final InMemoryVectorStore vectorStore;

    public KnowledgeIngestionDevController(
            KnowledgeIngestionService ingestionService, InMemoryVectorStore vectorStore) {

        this.ingestionService = ingestionService;
        this.vectorStore = vectorStore;
    }

    @PostMapping
    public List<EmbeddedChunk> ingest(
            @Valid @RequestBody IngestionRequest ingestionRequest) {

        return ingestionService.ingest(ingestionRequest.getText());
    }

    @GetMapping("/store")
    public List<EmbeddedChunk> getStoredEmbeddings() {

        return vectorStore.getAll();

    }

    @DeleteMapping("/store")
    public void clearStore() {

        vectorStore.clear();

    }
}