package com.sam.enterpriseai.ai.controller;

import com.sam.enterpriseai.ai.dto.IngestRequest;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ingestion")
public class IngestionController {

    private final EmbeddingStoreIngestor embeddingStoreIngestor;

    public IngestionController(EmbeddingStoreIngestor embeddingStoreIngestor) {
        this.embeddingStoreIngestor = embeddingStoreIngestor;
    }

    @PostMapping
    public ResponseEntity<Void> ingest(@RequestBody IngestRequest request) {

        embeddingStoreIngestor.ingest(Document.from(request.content()));

        return ResponseEntity.ok().build();
    }
}