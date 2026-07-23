# Enterprise AI Platform

An enterprise-grade AI backend platform built with **Java**, **Spring Boot** and **LangChain4j** to explore, understand and engineer modern AI systems using production-oriented software engineering principles.

Unlike typical AI demos, this repository is intentionally built as a long-term engineering platform that demonstrates **how enterprise AI systems evolve**—from understanding first principles to adopting mature frameworks while maintaining clean architecture and enterprise engineering standards.

> 🚧 **Project Status:** Active Development

---

# Vision

Enterprise AI Platform aims to become a reference implementation for designing enterprise AI backend systems.

The project focuses on:

- Enterprise software architecture
- Retrieval Augmented Generation (RAG)
- Large Language Model integration
- Knowledge management
- Vector databases
- AI platform engineering
- Extensible provider architecture
- Production-oriented backend development

The objective is not merely to integrate Large Language Models, but to understand, engineer and continuously evolve the surrounding platform that enterprises require to successfully operate AI workloads.

---

# Engineering Philosophy

Enterprise AI Platform follows a deliberate engineering approach.

Instead of immediately adopting high-level AI frameworks, the platform was intentionally developed in multiple stages.

The first stage focused on implementing the core concepts of Retrieval Augmented Generation (RAG) completely from first principles. The objective was to deeply understand how modern enterprise AI systems operate internally rather than treating frameworks as black boxes.

Core concepts that were implemented manually included:

- Document ingestion
- Document chunking
- Embedding generation
- Vector storage
- Semantic retrieval
- Prompt augmentation
- Provider abstraction
- End-to-end RAG orchestration

Once these architectural concepts were fully understood and validated, the platform deliberately transitioned to a framework-driven implementation using **Spring Boot** and **LangChain4j**.

This reflects a common enterprise engineering principle:

> **Understand the internals first. Adopt mature frameworks second. Build business capabilities third.**

As a result, the platform benefits from both worlds:

- A deep understanding of enterprise AI architecture.
- A production-oriented implementation built upon well-maintained enterprise frameworks.

---

# Project Evolution

The platform has intentionally evolved through multiple architectural phases.

## Phase 1 — First Principles

Understand enterprise AI by implementing the complete Retrieval Augmented Generation pipeline manually.

The objective of this phase was education, architectural understanding and experimentation.

Rather than relying on existing AI frameworks, the complete pipeline was implemented from scratch to gain practical knowledge of:

- Provider abstractions
- Knowledge ingestion
- Embedding pipelines
- Vector search
- Retrieval orchestration
- Prompt construction
- Context grounding

This phase established the architectural foundation of the platform.

---

## Phase 2 — Enterprise Framework Adoption (Current)

Once the underlying architecture was fully understood, the platform transitioned to Spring Boot and LangChain4j.

Responsibilities that belong to the framework are now delegated to the framework itself.

The platform now leverages:

- LangChain4j AI Services
- RetrievalAugmentor
- ContentRetriever
- ContentInjector
- EmbeddingStoreIngestor
- Spring Boot Dependency Injection

Custom code now focuses on enterprise responsibilities such as:

- Business capabilities
- Provider management
- Configuration
- Observability
- Clean architecture
- Platform extensibility

---

## Phase 3 — Enterprise AI Platform (Future)

The platform will continue evolving toward:

- Enterprise Knowledge Management
- Advanced Retrieval
- AI Agents
- MCP Integration
- Multi-tenancy
- Enterprise Observability
- Cloud-native Deployment

---

# Current Capabilities

## AI Platform

- Spring Boot architecture
- LangChain4j integration
- AI Services
- Provider-based architecture
- Runtime provider switching
- Configuration-driven design

---

## Large Language Models

- Ollama Chat Models
- Runtime configurable models
- HTTP request/response logging
- Production-ready configuration

---

## Embedding Platform

- Ollama Embedding Models
- Runtime configurable embedding providers
- Embedding abstraction
- Provider-based architecture

---

## Retrieval Augmented Generation (RAG)

- LangChain4j RetrievalAugmentor
- ContentRetriever
- ContentInjector
- EmbeddingStoreIngestor
- Enterprise prompt augmentation
- Semantic retrieval
- Context grounding
- Grounded response generation

---

## Knowledge Ingestion

- REST document ingestion endpoint
- Automatic document parsing
- Text segmentation
- Embedding generation
- Automatic vector indexing

---

## Vector Stores

Currently supported:

- InMemory Vector Store
- PostgreSQL + PgVector

Vector stores can be switched at runtime through Spring Boot configuration without changing application code.

---

## REST APIs

Current endpoints include:

- Enterprise Chat API
- Document Ingestion API

---

## Engineering

- Clean Architecture
- SOLID Principles
- Dependency Injection
- Provider Pattern
- Conditional Bean Loading
- Architecture Decision Records (ADR)
- Production logging
- Modular package organization

---

# High Level Architecture

```text
                         Enterprise AI Platform

                          REST Controllers
                                 │
                                 ▼
                       EnterpriseAssistant
                                 │
                                 ▼
                     LangChain4j AI Services
                                 │
                                 ▼
                      RetrievalAugmentor
                  ┌──────────────┴──────────────┐
                  ▼                             ▼
          ContentRetriever              ContentInjector
                  │                             │
                  ▼                             ▼
          Embedding Store               Prompt Construction
                  │
                  ▼
       PgVector / InMemory Vector Store
                  │
                  ▼
          Embedding Model Provider
                  │
                  ▼
                Ollama
                  │
                  ▼
             Chat Model Provider
                  │
                  ▼
                Ollama
```

---

# Knowledge Ingestion Flow

```text
Document Upload
       │
       ▼
Document Parser
       │
       ▼
Document Splitter
       │
       ▼
Embedding Model
       │
       ▼
EmbeddingStoreIngestor
       │
       ▼
PgVector / InMemory
```

---

# Question Answering Flow

```text
User Question
      │
      ▼
EnterpriseAssistant
      │
      ▼
RetrievalAugmentor
      │
      ▼
ContentRetriever
      │
      ▼
Vector Search
      │
      ▼
Relevant Knowledge
      │
      ▼
ContentInjector
      │
      ▼
Large Language Model
      │
      ▼
Grounded Response
```

---

# Technology Stack

## Backend

- Java 21
- Spring Boot
- Maven

## AI

- LangChain4j
- Ollama
- Retrieval Augmented Generation (RAG)

## Vector Databases

- InMemory
- PostgreSQL
- PgVector

## Testing

- JUnit 5
- Mockito

---

# Repository Structure

```text
enterprise-ai-platform
│
├── backend
│   ├── src
│   │   ├── main
│   │   │   ├── ai
│   │   │   │   ├── chat
│   │   │   │   ├── constants
│   │   │   │   ├── controller
│   │   │   │   ├── document
│   │   │   │   ├── dto
│   │   │   │   ├── embedding
│   │   │   │   ├── ingestion
│   │   │   │   ├── rag
│   │   │   │   ├── retrieval
│   │   │   │   ├── service
│   │   │   │   └── vectorstore
│   │   │   └── resources
│   │   └── test
│   └── pom.xml
│
├── docker
├── docs
│   └── ADR
├── postman
├── scripts
└── README.md
```

---

# Architecture Decision Records

Every significant engineering decision is documented under:

```
docs/ADR
```

Architectural changes are intentionally documented before implementation to preserve engineering reasoning and long-term maintainability.

---

# Development Roadmap

## ✅ Phase 1 — Enterprise AI Foundation

- Project Foundation
- Spring Boot Architecture
- LangChain4j Integration
- AI Services
- Provider Pattern
- Ollama Integration
- Runtime Provider Switching
- Embedding Pipeline
- Knowledge Ingestion
- Enterprise RAG Pipeline
- InMemory Vector Store
- PostgreSQL + PgVector
- REST APIs
- Architecture Decision Records

---

## 🚧 Phase 2 — Enterprise Knowledge Management

- PDF ingestion
- DOCX ingestion
- Markdown ingestion
- Metadata extraction
- Incremental indexing
- Knowledge versioning

---

## 🚧 Phase 3 — Advanced Retrieval

- Hybrid Search
- Metadata filtering
- Re-ranking
- Chunking strategies
- Embedding benchmarking
- Retrieval optimization

---

## 🚧 Phase 4 — Enterprise AI Agents

- Tool Calling
- Multi-agent architecture
- MCP integration
- Workflow orchestration
- Task planning

---

## 🚧 Phase 5 — Enterprise Platform

- Conversation memory
- Multi-tenancy
- Authentication
- Authorization
- Rate limiting
- API versioning

---

## 🚧 Phase 6 — Production Readiness

- Kafka integration
- Redis
- Prometheus
- Grafana
- Docker Compose
- Kubernetes
- Performance benchmarking
- Security hardening

---

# Design Principles

The platform follows several engineering principles:

- Understand before abstracting.
- Adopt mature frameworks where appropriate.
- Keep architecture modular.
- Configuration over hardcoding.
- Extensibility over vendor lock-in.
- Frameworks orchestrate.
- Custom code delivers business value.
- Every major architectural decision is documented through ADRs.

---

# Current Status

The Enterprise AI Platform has successfully established its core architectural foundation.

Current capabilities include:

- Enterprise LangChain4j RAG pipeline
- Runtime provider switching
- PgVector integration
- Knowledge ingestion
- Semantic retrieval
- Context injection
- REST APIs
- Production-oriented architecture
- Architecture documentation

Future milestones will focus on enterprise knowledge management, advanced retrieval strategies, AI agents and production platform capabilities.

---

# Database

The platform uses PostgreSQL with the PgVector extension.

Database schema evolution is managed exclusively through Flyway.

Hibernate is used only for ORM mapping and never manages schema creation or updates.

Database migrations are maintained under:

```
backend/src/main/resources/db/migration
```

---

# Local Development

## PostgreSQL

The project starts PostgreSQL using Docker.

### Host Machine

```
Host: localhost
Port: 5432
Database: enterprise_ai
Username: enterprise_ai
Password: enterprise_ai
```

### Docker Network

```
Host: postgres
Port: 5432
Database: enterprise_ai
Username: enterprise_ai
Password: enterprise_ai
```

### CloudBeaver

```
http://localhost:8978
```

---

# Author

This repository is part of my continuous journey toward becoming an **Enterprise AI Backend Engineer** by understanding, architecting and engineering modern AI systems using production-oriented software engineering practices.

The objective is not simply to integrate AI frameworks, but to understand the architecture behind them, make deliberate engineering decisions, document those decisions and continuously evolve the platform toward enterprise-grade capabilities.