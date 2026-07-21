package com.sam.enterpriseai.dto.ollama;

public record OllamaEmbeddingRequest(

        String model,

        String input

) {
}