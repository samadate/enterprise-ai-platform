CREATE TABLE knowledge_documents
(
    document_id      UUID PRIMARY KEY,

    document_name    VARCHAR(255) NOT NULL,

    document_type    VARCHAR(100) NOT NULL,

    source           VARCHAR(255),

    checksum         VARCHAR(128),

    status           VARCHAR(50) NOT NULL,

    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at       TIMESTAMP
);