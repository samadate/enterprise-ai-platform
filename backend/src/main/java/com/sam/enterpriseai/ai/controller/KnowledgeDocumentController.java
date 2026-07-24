package com.sam.enterpriseai.ai.controller;

import com.sam.enterpriseai.ai.dto.KnowledgeDocumentResponse;
import com.sam.enterpriseai.ai.service.KnowledgeDocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/knowledge")
public class KnowledgeDocumentController {

    private static final Logger log = LoggerFactory.getLogger(KnowledgeDocumentController.class);
    private final KnowledgeDocumentService knowledgeDocumentService;

    public KnowledgeDocumentController(KnowledgeDocumentService knowledgeDocumentService) {

        this.knowledgeDocumentService = knowledgeDocumentService;
    }

    @PostMapping(
            value = "/documents",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> uploadKnowledgeDocument(
            @RequestPart("file") MultipartFile file) throws IOException {

        if (!MediaType.TEXT_PLAIN_VALUE.equals(file.getContentType())) {
            return ResponseEntity
                    .unprocessableContent()
                    .body("Only text/plain is supported currently.");
        }

        UUID documentId = knowledgeDocumentService.ingestKnowledgeDocument(file);

        return ResponseEntity.ok(documentId.toString());
    }

    @GetMapping("/documents")
    public ResponseEntity<List<KnowledgeDocumentResponse>> getKnowledgeDocuments() {

        return ResponseEntity.ok(
                knowledgeDocumentService.getKnowledgeDocuments()
        );
    }

    @GetMapping("/documents/{documentId}")
    public ResponseEntity<KnowledgeDocumentResponse> getKnowledgeDocument(
            @PathVariable UUID documentId
    ) {

        return ResponseEntity.ok(
                knowledgeDocumentService.getKnowledgeDocument(documentId)
        );
    }
}