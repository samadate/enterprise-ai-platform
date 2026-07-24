package com.sam.enterpriseai.ai.service;

import com.sam.enterpriseai.ai.dto.KnowledgeDocumentResponse;
import com.sam.enterpriseai.ai.repository.KnowledgeDocument;
import com.sam.enterpriseai.ai.repository.KnowledgeDocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class KnowledgeDocumentService {

    private final KnowledgeDocumentRepository knowledgeDocumentRepository;
    private final KnowledgeIngestionService knowledgeIngestionService;

    public KnowledgeDocumentService(KnowledgeDocumentRepository knowledgeDocumentRepository, KnowledgeIngestionService knowledgeIngestionService) {
        this.knowledgeDocumentRepository = knowledgeDocumentRepository;
        this.knowledgeIngestionService = knowledgeIngestionService;
    }

    public UUID ingestKnowledgeDocument(MultipartFile file) throws IOException {
        return knowledgeIngestionService.ingest(file);
    }

    public List<KnowledgeDocumentResponse> getKnowledgeDocuments() {

        return knowledgeDocumentRepository
                .findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public KnowledgeDocumentResponse getKnowledgeDocument(UUID documentId) {

        KnowledgeDocument knowledgeDocument = knowledgeDocumentRepository
                .findByDocumentId(documentId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Knowledge document not found: " + documentId
                        ));

        return toResponse(knowledgeDocument);
    }

    private KnowledgeDocumentResponse toResponse(
            KnowledgeDocument document
    ) {

        return new KnowledgeDocumentResponse(
                document.getDocumentId(),
                document.getDocumentName(),
                document.getDocumentType(),
                document.getStatus(),
                document.getCreatedAt()
        );
    }

}