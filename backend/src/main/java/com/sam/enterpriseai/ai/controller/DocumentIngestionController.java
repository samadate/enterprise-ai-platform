package com.sam.enterpriseai.ai.controller;

import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.parser.TextDocumentParser;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/ingestion")
public class DocumentIngestionController {

    private final EmbeddingStoreIngestor embeddingStoreIngestor;

    public DocumentIngestionController(EmbeddingStoreIngestor embeddingStoreIngestor) {
        this.embeddingStoreIngestor = embeddingStoreIngestor;
    }

    @PostMapping(
            value = "/document",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> ingestDocument(
            @RequestPart("file") MultipartFile file) throws IOException {

        if (!MediaType.TEXT_PLAIN_VALUE.equals(file.getContentType())) {
            return ResponseEntity
                    .unprocessableContent()
                    .body("Only text/plain is supported currently.");
        }

        DocumentParser parser = new TextDocumentParser();

        Document document = parser.parse(file.getInputStream());

        embeddingStoreIngestor.ingest(document);

        return ResponseEntity.ok("Document ingested successfully.");
    }
}