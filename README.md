# Enterprise AI Platform

An enterprise-grade AI backend platform built with Java and Spring Boot to explore modern AI architecture from first principles.

The project demonstrates how Retrieval-Augmented Generation (RAG), vector search, provider abstraction, and enterprise backend design work internally without relying on high-level AI frameworks.

> 🚧 **Project Status:** Active Development

---

# Vision

The goal of this project is to build a production-inspired Enterprise AI Platform that demonstrates the architecture behind modern AI systems while following clean software engineering principles.

Rather than using frameworks such as LangChain or Spring AI, every major component is implemented from scratch to understand:

- Large Language Model (LLM) integration
- Retrieval-Augmented Generation (RAG)
- Embedding pipelines
- Vector databases
- Knowledge ingestion
- Provider abstraction
- Enterprise backend architecture
- Cloud-native design

---

# Current Features

## AI Provider Layer

- Provider-based architecture
- Ollama integration
- Configurable chat model
- Configurable embedding model
- Clean provider abstraction

---

## Knowledge Ingestion

- Sentence-aware document chunking
- Embedding generation
- Knowledge ingestion REST API
- Batch embedding support

---

## Vector Search

- VectorStore abstraction
- In-memory vector database implementation
- Cosine similarity search
- Top-K retrieval

---

## Retrieval-Augmented Generation (RAG)

- Query embedding
- Semantic similarity search
- Prompt construction
- Context grounding
- End-to-end RAG pipeline

---

## Engineering Practices

- Clean Architecture
- SOLID Principles
- Dependency Inversion
- Provider Pattern
- Configuration Properties
- Conditional Bean Loading
- Unit Tested Core Components
- Architecture Decision Records (ADR)

---

# Current Architecture

```text
                     KNOWLEDGE INGESTION

Document
    │
    ▼
SentenceAwareChunkingService
    │
    ▼
EmbeddingService
    │
    ▼
EmbeddingProvider
    │
    ▼
VectorStore
```

```text
                    QUESTION ANSWERING

REST Controller
       │
       ▼
AIService
       │
       ▼
RAGService
       │
       ├───────────────┐
       ▼               ▼
RetrievalService   PromptBuilder
       │
       ▼
EmbeddingService
       │
       ▼
VectorStore
       │
       ▼
LLMProvider
       │
       ▼
Ollama
```

---

# Tech Stack

## Current

- Java 21
- Spring Boot
- Ollama
- Maven
- JUnit 5
- Mockito

## Planned

- PostgreSQL
- pgvector
- Apache Kafka
- Redis
- Docker Compose
- Prometheus
- Grafana
- Angular

---

# Repository Structure

```text
backend/
docker/
docs/
architecture/
prompts/
scripts/
postman/
```

---

# Development Roadmap

## ✅ Completed

- [x] Project Foundation
- [x] AI Provider Architecture
- [x] Ollama Chat Integration
- [x] Embedding Pipeline
- [x] Sentence-Aware Chunking
- [x] Knowledge Ingestion Pipeline
- [x] In-Memory Vector Store
- [x] Semantic Retrieval
- [x] Prompt Builder
- [x] End-to-End Retrieval-Augmented Generation (RAG)

---

## 🚧 Next Milestones

- [ ] PostgreSQL + pgvector Integration
- [ ] PDF Knowledge Ingestion
- [ ] Metadata-aware Retrieval
- [ ] Conversation Memory
- [ ] Multi-turn Chat
- [ ] AI Agents
- [ ] Kafka Event Pipeline
- [ ] Observability (Prometheus + Grafana)
- [ ] Docker Compose Deployment
- [ ] Production Hardening

---

# Design Principles

This project follows a few core principles:

- Understand before abstracting.
- Keep architecture simple and extensible.
- Build features incrementally.
- Prefer provider abstractions over vendor lock-in.
- Write testable, maintainable code.
- Focus on enterprise-grade design rather than quick prototypes.

---

# Project Philosophy

This repository is not intended to be another chatbot demo.

It is a long-term engineering project that incrementally builds an Enterprise AI Platform while exploring the architecture behind modern AI systems.

Each feature is added only after understanding the underlying concepts and making deliberate architectural decisions.

---

# Current Status

✅ End-to-End Retrieval-Augmented Generation (RAG) is fully functional.

The platform currently supports:

- Knowledge ingestion
- Semantic chunking
- Embedding generation
- Vector storage
- Semantic retrieval
- Prompt construction
- Grounded answer generation

The next major milestone is replacing the in-memory vector store with a production-grade PostgreSQL + pgvector implementation without changing the upper layers of the application.


## Database

The platform uses PostgreSQL with the pgvector extension.

Database schema evolution is managed exclusively through Flyway.

Hibernate is configured only for ORM mapping and never manages schema
creation or updates.

All database changes are introduced through versioned migrations under:

backend/src/main/resources/db/migration

## Local PostgreSQL

The project starts PostgreSQL automatically using Docker.

Connection from the host machine:

Host: localhost
Port: 5432
Database: enterprise_ai
Username: enterprise_ai
Password: enterprise_ai

CloudBeaver:

URL: http://localhost:8978

Connection inside Docker network:

Host: postgres
Port: 5432
Database: enterprise_ai
Username: enterprise_ai
Password: enterprise_ai

---


**Author**

This repository is part of my continuous journey toward becoming an Enterprise AI Backend Engineer by understanding and building modern AI systems from first principles.