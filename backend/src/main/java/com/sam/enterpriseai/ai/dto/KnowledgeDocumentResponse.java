package com.sam.enterpriseai.ai.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record KnowledgeDocumentResponse(

        UUID documentId,
        String documentName,
        String documentType,
        String status,
        LocalDateTime createdAt

) {
}