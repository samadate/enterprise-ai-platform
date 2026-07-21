package com.sam.enterpriseai.controller;

import com.sam.enterpriseai.dto.IngestionRequest;
import com.sam.enterpriseai.knowledge.embedding.EmbeddedChunk;
import com.sam.enterpriseai.knowledge.ingestion.KnowledgeIngestionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/knowledge")
public class KnowledgeController {

    private final KnowledgeIngestionService knowledgeIngestionService;

    public KnowledgeController(
            KnowledgeIngestionService knowledgeIngestionService) {

        this.knowledgeIngestionService = knowledgeIngestionService;
    }

    @PostMapping("/ingest")
    public List<EmbeddedChunk> ingest(
            @Valid @RequestBody IngestionRequest request) {

        return knowledgeIngestionService.ingest(request.getText());
    }
}