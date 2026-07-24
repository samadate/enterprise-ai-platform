package com.sam.enterpriseai.ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface KnowledgeDocumentRepository
        extends JpaRepository<KnowledgeDocument, UUID> {
    List<KnowledgeDocument> findAllByOrderByCreatedAtDesc();
    Optional<KnowledgeDocument> findByDocumentId(UUID documentId);
}