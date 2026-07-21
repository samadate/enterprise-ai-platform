package com.sam.enterpriseai.dto.ollama;

import java.util.List;

public record OllamaEmbeddingResponse(

        String model,

        List<List<Double>> embeddings

) {
}