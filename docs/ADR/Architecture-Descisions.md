# Enterprise AI Platform

# Architecture Decision Record (ADR)

This document captures the significant architectural decisions made during the development of the Enterprise AI Platform.

Each decision explains not only **what** was chosen but also **why** it was chosen.

---

## ADR-001 : Build the AI Platform without AI Frameworks

### Status

Accepted

### Decision

The platform will be implemented from scratch without using frameworks such as LangChain4j.

### Reason

The objective of this project is to deeply understand how Enterprise AI systems work internally rather than learning a framework.

Core concepts such as:

- Chat
- Embeddings
- Chunking
- Retrieval
- Prompt Construction
- Vector Search

will all be implemented manually.

### Consequence

Pros

- Deep understanding of Enterprise AI architecture.
- Complete control over implementation.
- Framework-independent design.

Cons

- More implementation effort.

---

## ADR-002 : Capability-Based Provider Architecture

### Status

Accepted

### Decision

Separate providers for each AI capability.

Instead of:

AIProvider

the platform uses:

- LLMProvider
- EmbeddingProvider

Future capabilities will introduce additional providers if required.

### Reason

Different AI capabilities evolve independently.

Following the Interface Segregation Principle keeps contracts small and focused.

### Consequence

New providers can be added independently without impacting unrelated capabilities.

---

## ADR-003 : Provider Abstraction

### Status

Accepted

### Decision

Business services depend on provider interfaces rather than provider implementations.

Example

AIService → LLMProvider

instead of

AIService → OllamaLLMProvider

### Reason

Supports multiple providers.

Examples

- Ollama
- OpenAI
- Gemini
- Claude

without changing business logic.

### Consequence

Supports Open/Closed Principle and Dependency Inversion Principle.

---

## ADR-004 : Separate Chat and Embedding Configuration

### Status

Accepted

### Decision

Chat and Embedding are configured independently.

Example

ai.chat.*

ai.embedding.*

instead of

ai.ollama.*

### Reason

Configuration should describe platform capabilities rather than implementation vendors.

This allows future providers without redesigning configuration.

### Consequence

Provider implementation becomes transparent to consumers.

---

## ADR-005 : Treat Knowledge as a First-Class Module

### Status

Accepted

### Decision

Knowledge functionality will exist as its own module.

Example

knowledge/

- chunk
- ingestion
- vector
- retrieval

### Reason

Knowledge management represents a core business capability of the platform rather than a collection of helper services.

### Consequence

The architecture remains modular and easier to evolve.

---

## ADR-006 : DTOs and Domain Models are Different

### Status

Accepted

### Decision

Transport objects remain under dto.

Business concepts remain under their respective domain modules.

Examples

DTO

- AIRequest
- AIResponse

Domain

- Chunk
- Document
- SearchResult

### Reason

Separates API contracts from business concepts.

### Consequence

Improves maintainability and Domain-Driven Design.

---

## ADR-007 : Strategy Pattern for Extensible Components

### Status

Accepted

### Decision

Use interfaces for replaceable algorithms.

Examples

- LLMProvider
- EmbeddingProvider
- ChunkingService

### Reason

Allows new implementations without modifying existing consumers.

### Consequence

Supports future extensibility.

---

## ADR-008 : Configuration Ownership by Module

### Status

Proposed

### Decision

Replace the centralized AIProperties with module-specific property classes.

Examples

- ChatProperties
- EmbeddingProperties
- ChunkProperties

Each configuration class owns a single configuration namespace.

### Reason

Prevents configuration classes from becoming large and violating the Single Responsibility Principle.

### Consequence

Improves modularity and maintainability as the platform grows.

---

## ADR-009 : RAG Pipeline Built Incrementally

### Status

Accepted

### Decision

Implement the RAG pipeline incrementally.

Order

1. Chunking
2. Embedding
3. Vector Storage
4. Retrieval
5. Prompt Construction
6. LLM Response

### Reason

Each stage can be independently tested before integration.

### Consequence

Simplifies debugging and improves architecture clarity.

---

## ADR-010 : Separate Learning Repositories

### Status

Accepted

### Decision

This repository focuses exclusively on Enterprise AI architecture.

Framework-specific implementations (e.g. LangChain4j) will be developed in separate repositories.

### Reason

Keeps this project framework-independent while allowing dedicated learning projects for AI frameworks.

### Consequence

The repository remains a showcase of Enterprise AI engineering rather than framework usage.

## ADR-011 : Documentation and Repository Evolution

### Status

Accepted

### Decision

The repository will evolve incrementally. Documentation files, folders, and project artifacts will only be created when they serve an active purpose.

Empty Markdown files, placeholder folders, and speculative project structures will not be maintained.

### Reason

Documentation should evolve alongside the software rather than being scaffolded upfront.

Following the YAGNI (You Aren't Gonna Need It) principle keeps the repository clean, intentional, and easier to navigate.

Every file and folder should justify its existence by providing immediate value to contributors.

### Consequence

Pros

- Cleaner repository structure.
- Documentation remains relevant and maintained.
- Eliminates placeholder files that quickly become outdated.
- Encourages incremental and purposeful project evolution.

Cons

- New documentation structure may need to be introduced later as the project grows.

### Related Principles

- YAGNI (You Aren't Gonna Need It)
- Clean Architecture
- Incremental Design

## ADR-012 : Module-Owned Configuration

### Status

Accepted

### Decision

Replace the centralized `AIProperties` configuration class with module-specific configuration classes.

Each functional module owns its own configuration.

Examples

- ChatProperties
- EmbeddingProperties
- ChunkProperties

Each class is mapped to its own configuration namespace using `@ConfigurationProperties`.

### Reason

The centralized configuration class would continue to grow as new platform capabilities are introduced.

Splitting configuration by module follows the Single Responsibility Principle and aligns configuration ownership with module ownership.

Each module becomes independently configurable, easier to understand, easier to test, and easier to evolve.

### Consequence

Pros

- Better adherence to the Single Responsibility Principle.
- Smaller and focused configuration classes.
- Improved modularity.
- Easier future maintenance.
- Clear ownership of configuration.

Cons

- Slightly more configuration classes.

### Related Principles

- Single Responsibility Principle (SRP)
- Separation of Concerns
- Modular Architecture

## ADR-012 : Capability-Oriented Configuration Model

### Status

Accepted

### Decision

Replace the centralized `AIProperties` configuration with capability-specific configuration classes.

Each AI capability owns its own configuration.

Examples

- ChatProperties
- EmbeddingProperties

Each capability is responsible for configuring:

- Provider
- Base URL
- Model

Provider-specific implementation details such as REST API endpoints remain inside the provider implementation classes and are not exposed through application configuration.

### Reason

Configuration should describe application capabilities rather than implementation details.

This approach removes coupling between the configuration model and specific AI providers while allowing different providers to be selected independently for each capability.

Examples

- Chat → Ollama
- Embedding → Ollama

or

- Chat → OpenAI
- Embedding → VoyageAI

without requiring architectural changes.

Provider-specific API contracts (such as `/api/generate` or `/api/embed`) are implementation details and therefore belong inside provider implementations.

### Consequence

Pros

- Strong adherence to the Single Responsibility Principle.
- Capability-oriented configuration.
- Provider-independent configuration model.
- Supports different providers for different capabilities.
- Easier future extensibility.

Cons

- Slightly more configuration classes.

### Related Principles

- Single Responsibility Principle
- Separation of Concerns
- Open/Closed Principle
- Capability-Oriented Design

# ADR-013: Use JDK BreakIterator for Initial Document Chunking

**Status:** Accepted

## Context

The Knowledge module requires sentence-aware document chunking as the first stage of the RAG ingestion pipeline. Several approaches were considered, including custom sentence parsing, regular expressions, Java's built-in `BreakIterator`, and external NLP libraries such as OpenNLP and LangChain4J.

The primary goal of this project is to learn and build an enterprise-grade RAG platform rather than implementing NLP algorithms from scratch.

## Decision

The project will use Java's built-in `BreakIterator` for sentence detection.

The chunking implementation will:
- Detect sentences using `BreakIterator`
- Group a configurable number of sentences into `Chunk` objects
- Keep the implementation simple, dependency-free, and easily replaceable

No custom sentence detection logic will be implemented.

## Rationale

- Keeps the project focused on RAG architecture rather than NLP implementation.
- Avoids reinventing capabilities already provided by the JDK.
- Introduces no additional dependencies.
- Provides sufficient sentence detection quality for the initial implementation.
- Allows future replacement with more advanced chunking strategies without changing the surrounding pipeline.

## Known Limitations

`BreakIterator` is not a complete NLP solution and may incorrectly split sentences containing:
- Abbreviations (e.g. `Dr.`, `Mr.`, `Inc.`)
- Acronyms (e.g. `U.S.`, `U.K.`)
- Certain punctuation edge cases

These limitations are accepted for the current version.

## Consequences

The initial document ingestion pipeline becomes:

```
Text
    ↓
BreakIterator
    ↓
Chunking
    ↓
Embeddings
    ↓
Vector Store
```

Future implementations may replace `BreakIterator` with OpenNLP, LangChain4J, or semantic chunking techniques without impacting the remainder of the ingestion pipeline, preserving the Open/Closed Principle.

# ADR-014: Provider-Owned Vector Store Models and Mapping

- **Status:** Accepted
- **Date:** 2026-07-21
- **Decision Makers:** Project Maintainer

---

# Context

The Enterprise AI Platform currently represents generated embeddings using the domain model:

```text
EmbeddedChunk
```

This model is produced by the Knowledge domain after document chunking and embedding generation.

Initially, the `InMemoryVectorStore` stored `EmbeddedChunk` objects directly.

As the platform evolves toward production-grade vector databases (PostgreSQL + pgvector, Qdrant, Pinecone, Milvus, etc.), each provider introduces its own persistence requirements, including provider-specific identifiers, metadata, namespaces, indexing strategies, and storage representations.

Allowing provider implementations to persist the domain model directly would tightly couple the Knowledge domain with persistence concerns and make provider implementations inconsistent.

---

# Decision

Every Vector Store provider owns:

- its storage model
- its mapping implementation
- its persistence logic

The Knowledge domain remains completely unaware of how providers persist embeddings.

The Knowledge domain continues to expose:

```text
EmbeddedChunk
```

Each provider is responsible for translating this domain model into its own persistence representation.

Example:

```text
Knowledge Domain

EmbeddedChunk
        │
        ▼

Provider Mapper

        │
        ▼

Provider Storage Model

        │
        ▼

Provider Implementation
```

Examples:

```text
EmbeddedChunk
        │
        ▼
InMemoryMapper
        │
        ▼
InMemoryVector
        │
        ▼
InMemoryVectorStore
```

```text
EmbeddedChunk
        │
        ▼
PgVectorMapper
        │
        ▼
PgVectorRecord
        │
        ▼
PgVectorStore
```

```text
EmbeddedChunk
        │
        ▼
QdrantMapper
        │
        ▼
QdrantPoint
        │
        ▼
QdrantVectorStore
```

---

# Consequences

## Advantages

- Preserves separation between the Knowledge domain and persistence.
- Prevents provider-specific concerns from leaking into domain models.
- Allows each provider to evolve independently.
- Keeps the `VectorStore` abstraction stable.
- Makes adding new providers straightforward.
- Maintains architectural consistency across all vector store implementations.
- Simplifies provider-specific testing.
- Follows the Adapter Pattern.

---

## Trade-offs

- Introduces an additional mapping step inside each provider.
- Adds a small amount of provider-specific code.
- Requires each provider to maintain its own storage model.

These trade-offs are considered acceptable because they preserve long-term architectural flexibility.

---

# Alternatives Considered

## Option 1 — Persist `EmbeddedChunk` directly

Rejected.

Reasons:

- Couples the Knowledge domain to persistence.
- Forces domain models to evolve whenever storage requirements change.
- Makes providers inconsistent.

---

## Option 2 — Shared generic persistence model

Example:

```text
VectorDocument
```

Rejected.

Reasons:

- Introduces another abstraction layer without eliminating provider-specific mapping.
- Still requires provider-specific translation.
- Becomes a "lowest common denominator" model that belongs to neither the domain nor any provider.

---

## Option 3 — Provider-owned models (Chosen)

Accepted.

Each provider defines:

- storage model
- mapper
- persistence implementation

The Knowledge domain remains persistence-agnostic.

---

# Architecture Impact

This decision establishes a consistent provider architecture for all current and future vector store implementations.

Future providers such as PostgreSQL + pgvector, Qdrant, Pinecone, Milvus, Elasticsearch, or Redis Vector Search will follow the same implementation pattern without requiring changes to the Knowledge domain.

This ADR reinforces the project's architectural principle that **domain models should remain independent of infrastructure concerns**.


# ADR-015: Educational Foundation Completion and Enterprise Transition

## Status

Accepted

---

## Context

The initial objective of this project was to understand Enterprise AI systems by implementing the core building blocks from first principles instead of relying immediately on mature AI frameworks.

Up to version v0.2.0, the platform successfully demonstrates:

- Provider-based architecture
- Custom Retrieval-Augmented Generation (RAG)
- Document chunking
- Embedding generation
- In-memory vector storage
- Knowledge ingestion
- Similarity search
- Context augmentation
- End-to-end RAG pipeline

These implementations were intentionally educational rather than production-oriented.

The remaining educational objective is to understand production vector persistence through PostgreSQL and pgvector.

---

## Decision

The project roadmap is divided into three distinct phases.

### Phase 1 — Educational Foundation (v0.x)

Purpose:

Understand the internal mechanics of Enterprise AI systems by implementing important concepts ourselves where educational value exists.

This phase includes:

- Custom RAG pipeline
- Provider abstractions
- InMemory Vector Store
- PostgreSQL
- pgvector
- Docker Compose
- Spring Data JPA
- Flyway

Version v0.3.0 will represent the completion of this educational phase.

---

### Phase 2 — Enterprise Transition

After v0.3.0 the project will gradually transition toward enterprise-grade implementations.

Instead of extending custom RAG algorithms indefinitely, mature frameworks will be evaluated and integrated where appropriate.

Examples include:

- LangChain4j
- Spring AI
- Production Retrieval
- AI Memory
- AI Agents
- Tool Calling

Custom educational implementations may be replaced when they no longer provide additional educational value.

---

### Phase 3 — Enterprise AI Platform

The long-term objective becomes building production-inspired enterprise AI systems rather than reimplementing solved algorithms.

Future development will focus on:

- Enterprise architecture
- Scalability
- Infrastructure
- Observability
- Security
- Cloud-native deployment
- Multi-provider support
- Distributed systems

---

## Consequences

### Advantages

- Educational implementations remain preserved through Git history and tagged releases.
- The project naturally evolves toward enterprise engineering practices.
- Framework adoption becomes a conscious architectural decision rather than an initial dependency.
- The repository demonstrates both understanding of AI fundamentals and practical enterprise implementation.

### Trade-offs

- Some educational implementations may eventually become obsolete in the active development branch.
- Git history and milestone releases become the permanent record of educational implementations instead of maintaining multiple active implementations simultaneously.

---

## Educational Milestone

Version **v0.3.0** is defined as the completion of the educational implementation phase.

Future versions will emphasize enterprise integration and production-grade engineering practices while preserving architectural ownership of the platform.


# ADR-016: PostgreSQL and pgvector Persistence Architecture

## Status

Accepted

---

## Context

The educational implementation currently uses an in-memory vector store to understand vector storage, similarity search and Retrieval-Augmented Generation (RAG).

The next educational milestone is to understand production-grade vector persistence using PostgreSQL with the pgvector extension while preserving the provider-based architecture established throughout the platform.

The persistence implementation should remain simple, educational and aligned with enterprise software engineering practices without introducing unnecessary abstraction layers.

---

## Decision

### 1. PostgreSQL

PostgreSQL will be the primary relational database for the platform.

The pgvector extension will be enabled to provide vector similarity capabilities.

Future non-AI application data will also use the same PostgreSQL instance.

---

### 2. Docker Compose

PostgreSQL will run inside Docker Compose.

Reasons:

- Reproducible development environment
- Version controlled infrastructure
- Easy onboarding
- Enterprise development workflow
- No local PostgreSQL installation required

Persistent Docker volumes will be used so that database data survives container recreation.

---

### 3. Spring Data JPA

Spring Data JPA will be used for persistence.

Reasons:

- Focus remains on pgvector rather than JDBC implementation
- Enterprise standard
- Reduced boilerplate
- Easy future evolution

---

### 4. Provider Ownership

The pgvector implementation belongs entirely to the Vector Store Provider.

Application layers must never interact directly with PostgreSQL entities.

The provider remains responsible for persistence concerns.

---

### 5. Provider Model

A provider-specific persistence model will be introduced.

Example:

PgVectorRecord

This model represents database persistence only.

It must never leak outside the provider package.

---

### 6. Provider Mapper

A provider mapper converts between:

EmbeddedChunk

and

PgVectorRecord

This keeps domain models independent from persistence technologies.

---

### 7. Repository

Spring Data requires a repository interface.

The repository exists solely as a framework requirement.

It is not considered an architectural layer.

It will not be wrapped by additional gateway or repository abstractions.

Only PgVectorStoreProvider will interact with the repository.

---

### 8. Package Structure

provider
└── vectorstore
└── pgvector
├── model
├── mapper
├── PgVectorRepository
└── PgVectorStoreProvider

No additional repository package or gateway layer will be introduced.

---

### 9. Similarity Search

Similarity search will be delegated entirely to PostgreSQL through pgvector operators.

The Java application will not calculate cosine similarity.

Database responsibilities remain inside the database.

---

### 10. Provider Contract

PgVectorStoreProvider must implement the existing VectorStore abstraction.

No application code should require modification when switching between:

- InMemory
- pgvector

Only configuration should determine the active provider.

---

## Consequences

### Advantages

- Clear provider ownership
- Simple architecture
- Minimal abstractions
- Enterprise-aligned persistence
- Framework-independent domain model
- Easy future provider additions

### Trade-offs

- Provider contains persistence knowledge
- Spring Data repository remains as a framework artifact
- PostgreSQL-specific features remain isolated inside the provider

---

## Educational Goal

The objective of this implementation is not only to store vectors.

It is to understand how production vector databases integrate into enterprise Java applications while preserving clean architecture boundaries.


# ADR-017: Database Schema Ownership and pgvector Initialization

## Status

Accepted

---

## Context

During the initial PostgreSQL and pgvector infrastructure setup, the project attempted
to enable the `vector` extension using PostgreSQL's initialization mechanism
(`/docker-entrypoint-initdb.d/init.sql`).

Although this approach works for brand-new database initialization, it introduces
multiple limitations:

- Initialization scripts execute only once during the first database creation.
- Existing Docker volumes prevent re-execution of initialization scripts.
- Database evolution becomes partially managed by Docker and partially by the application.
- Infrastructure concerns become tightly coupled with database schema evolution.

The project already uses Flyway as the database migration tool, whose responsibility
is to manage database schema changes in a version-controlled and repeatable manner.

---

## Decision

The project adopts the following responsibility separation:

### Docker

Docker is responsible only for provisioning infrastructure.

For PostgreSQL this means:

- Running PostgreSQL.
- Providing the pgvector-enabled PostgreSQL image.
- Persisting database storage.
- Exposing networking.

Docker **must not** modify application database schema.

---

### Flyway

Flyway becomes the single owner of the application database schema.

This includes:

- Enabling PostgreSQL extensions.
- Creating schemas.
- Creating tables.
- Creating indexes.
- Managing future database evolution.

The first Flyway migration will enable pgvector using:

```sql
CREATE EXTENSION IF NOT EXISTS vector;
```

---

## Consequences

### Positive

- Single source of truth for database evolution.
- Version-controlled schema changes.
- Repeatable across Local, CI, QA and Production.
- Eliminates dependency on PostgreSQL initialization scripts.
- Better separation between infrastructure and application responsibilities.
- Aligns with enterprise database migration practices.

### Negative

- Application must execute Flyway migrations before pgvector can be used.
- Docker alone no longer provisions a fully initialized application database.

---

## Alternatives Considered

### Option A

Enable pgvector using Docker initialization scripts.

Rejected because:

- Runs only during first initialization.
- Difficult to evolve over time.
- Splits schema ownership between Docker and Flyway.

---

### Option B (Accepted)

Provision PostgreSQL using Docker.

Manage all schema evolution, including pgvector extension creation, through Flyway.

Accepted because it provides a cleaner separation of concerns and follows enterprise
database migration practices.

---

## Related ADRs

- ADR-014 — Storage Model Isolation
- ADR-016 — Local Development Infrastructure


# ADR-018 — Database Migration Strategy

## Status

Accepted

## Context

The Enterprise AI Platform requires a repeatable and deterministic
database initialization process across development, testing, and future
production environments.

Historically, database schema creation can be delegated to ORM tools
(e.g. Hibernate) or managed externally.

To maintain full control over schema evolution and ensure reproducible
deployments, the platform requires an explicit migration strategy.

## Decision

The platform adopts Flyway as the single source of truth for database
schema evolution.

The following rules apply:

- Every database change must be introduced through a Flyway migration.
- PostgreSQL extensions (including pgvector) are installed through
  migrations.
- Hibernate is not permitted to create or update database schema.
- Spring Data JPA is responsible only for persistence mapping.
- Database schema history is maintained exclusively by Flyway.
- Docker images remain generic and contain no project-specific schema
  creation logic.

## Consequences

Positive

- Deterministic database initialization.
- Version-controlled schema evolution.
- Safe upgrades across environments.
- Production-compatible deployment model.
- Clear separation between persistence mapping and schema management.

Negative

- Every schema modification requires creating a new migration.
- Developers must avoid relying on Hibernate auto-DDL features.

## Alternatives Considered

### Hibernate ddl-auto

Rejected.

Although convenient during development, automatic schema generation is
not suitable for production systems and introduces uncontrolled schema
changes.

### SQL initialization scripts only

Rejected.

Plain initialization scripts lack schema versioning and upgrade
capabilities.

Flyway provides a mature migration framework with auditability and
incremental evolution.


# ADR-018 — Database Migration Strategy

## Status

Accepted

## Context

The Enterprise AI Platform requires a repeatable and deterministic
database initialization process across development, testing, and future
production environments.

Historically, database schema creation can be delegated to ORM tools
(e.g. Hibernate) or managed externally.

To maintain full control over schema evolution and ensure reproducible
deployments, the platform requires an explicit migration strategy.

## Decision

The platform adopts Flyway as the single source of truth for database
schema evolution.

The following rules apply:

- Every database change must be introduced through a Flyway migration.
- PostgreSQL extensions (including pgvector) are installed through
  migrations.
- Hibernate is not permitted to create or update database schema.
- Spring Data JPA is responsible only for persistence mapping.
- Database schema history is maintained exclusively by Flyway.
- Docker images remain generic and contain no project-specific schema
  creation logic.

## Consequences

Positive

- Deterministic database initialization.
- Version-controlled schema evolution.
- Safe upgrades across environments.
- Production-compatible deployment model.
- Clear separation between persistence mapping and schema management.

Negative

- Every schema modification requires creating a new migration.
- Developers must avoid relying on Hibernate auto-DDL features.

## Alternatives Considered

### Hibernate ddl-auto

Rejected.

Although convenient during development, automatic schema generation is
not suitable for production systems and introduces uncontrolled schema
changes.

### SQL initialization scripts only

Rejected.

Plain initialization scripts lack schema versioning and upgrade
capabilities.

Flyway provides a mature migration framework with auditability and
incremental evolution.

# ADR-019 - Transition from Educational RAG to Enterprise AI Platform

## Status

Accepted

---

## Context

Version **v0.2.0** successfully achieved its educational objective.

The platform now contains a complete Retrieval-Augmented Generation (RAG) implementation built from first principles, including:

- Manual sentence chunking
- Manual embedding generation
- Custom InMemoryVectorStore
- Custom similarity search
- Provider abstraction
- Clean architecture
- Configuration driven execution

These implementations were intentionally written to understand the internal mechanics of modern RAG systems.

The educational objective has now been achieved.

Future development should focus on building an enterprise-ready AI platform rather than maintaining duplicate implementations of production-grade libraries.

---

## Decision

Beginning with **v0.3.0**, the project transitions from an educational RAG implementation to an Enterprise AI Platform.

The project will gradually replace educational implementations with production-grade components wherever appropriate.

The platform will adopt LangChain4j as the canonical implementation for:

- Chunk representation
- Embedding pipeline
- Retrieval pipeline
- Embedding Store contracts

The platform itself will continue to own:

- Configuration
- Provider selection
- Spring Boot integration
- Docker infrastructure
- Observability
- Metrics
- Health
- Enterprise workflows
- Lifecycle management

---

## Educational Milestone

Version **v0.2.0** officially concludes the educational implementation of the RAG pipeline.

The custom RAG implementation enters maintenance mode and becomes deprecated.

It remains available only as a historical learning reference.

---

## Enterprise Direction

Future development focuses on:

- LangChain4j integration
- Production-grade PgVector provider
- Enterprise metadata
- Hybrid retrieval
- Reranking
- Enterprise observability
- Multi-provider support
- Knowledge management

---

## Architectural Principle

The platform owns architecture.

Libraries own algorithms.

The Enterprise AI Platform orchestrates AI components rather than reimplementing them.

---

## Consequences

### Positive

- Reduced maintenance
- Industry-standard APIs
- Easier future upgrades
- Better enterprise alignment
- Cleaner architecture
- Faster feature development

### Negative

- Educational RAG classes will gradually be removed
- Existing custom implementations become deprecated
- Future development depends on LangChain4j APIs

---

## Future Work

v0.3.0 completes the Enterprise Transition.

Future releases focus on enterprise capabilities instead of educational implementations.