package com.sam.enterpriseai.ai.service;

import com.sam.enterpriseai.ai.repository.KnowledgeDocument;
import com.sam.enterpriseai.ai.repository.KnowledgeDocumentRepository;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class KnowledgeIngestionService {

    private final KnowledgeDocumentRepository repository;
    private final EmbeddingStoreIngestor embeddingStoreIngestor;

    public KnowledgeIngestionService(KnowledgeDocumentRepository repository,
                                     EmbeddingStoreIngestor embeddingStoreIngestor
    ) {
        this.repository = repository;
        this.embeddingStoreIngestor = embeddingStoreIngestor;
    }

    KnowledgeDocument createKnowledgeDocument(MultipartFile file) {

        KnowledgeDocument knowledgeDocument = new KnowledgeDocument();

        knowledgeDocument.setDocumentId(UUID.randomUUID());
        knowledgeDocument.setDocumentName(file.getOriginalFilename());
        knowledgeDocument.setDocumentType(file.getContentType());
        knowledgeDocument.setStatus("ACTIVE");
        knowledgeDocument.setCreatedAt(LocalDateTime.now());

        return repository.save(knowledgeDocument);
    }

    Document parseDocument(MultipartFile file) throws IOException {

        DocumentParser parser = new TextDocumentParser();

        return parser.parse(file.getInputStream());
    }

    Document enrichDocument(
            Document document,
            KnowledgeDocument knowledgeDocument
    ) {

        Metadata metadata = document.metadata();

        metadata.put(
                "documentId",
                knowledgeDocument.getDocumentId().toString()
        );

        metadata.put(
                "documentName",
                knowledgeDocument.getDocumentName()
        );

        metadata.put(
                "documentType",
                knowledgeDocument.getDocumentType()
        );

        return Document.from(
                document.text(),
                metadata
        );
    }

    public UUID ingest(MultipartFile file) throws IOException {

        KnowledgeDocument knowledgeDocument =
                createKnowledgeDocument(file);

        Document document =
                parseDocument(file);

        Document enrichedDocument =
                enrichDocument(
                        document,
                        knowledgeDocument
                );

        embeddingStoreIngestor.ingest(enrichedDocument);

        return knowledgeDocument.getDocumentId();
    }

}