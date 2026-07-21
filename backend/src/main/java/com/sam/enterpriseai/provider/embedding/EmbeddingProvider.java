package com.sam.enterpriseai.provider.embedding;

import java.util.List;

public interface EmbeddingProvider {

    List<Double> generateEmbedding(String text);

}