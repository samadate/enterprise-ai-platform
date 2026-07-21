package com.sam.enterpriseai.knowledge.embedding;

import java.util.List;

public class EmbeddedChunk {

    private String content;
    private List<Double> embedding;

    public EmbeddedChunk() {
    }

    public EmbeddedChunk(String content, List<Double> embedding) {
        this.content = content;
        this.embedding = embedding;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Double> getEmbedding() {
        return embedding;
    }

    public void setEmbedding(List<Double> embedding) {
        this.embedding = embedding;
    }
}