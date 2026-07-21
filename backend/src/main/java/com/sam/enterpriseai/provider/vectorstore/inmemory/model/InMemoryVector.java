package com.sam.enterpriseai.provider.vectorstore.inmemory.model;

import java.util.List;

public class InMemoryVector {

    private final String content;
    private final List<Double> embedding;

    public InMemoryVector(
            String content,
            List<Double> embedding) {

        this.content = content;
        this.embedding = embedding;
    }

    public String getContent() {
        return content;
    }

    public List<Double> getEmbedding() {
        return embedding;
    }
}