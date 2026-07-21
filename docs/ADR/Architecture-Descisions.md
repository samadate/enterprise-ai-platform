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