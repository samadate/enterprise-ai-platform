package com.sam.enterpriseai.controller.dev;

import com.sam.enterpriseai.provider.embedding.EmbeddingProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Temporary controller to test the embedding endpoints.
 * */
@RestController
@RequestMapping("/api/v1/dev")
public class EmbeddingDevController {

    private final EmbeddingProvider embeddingProvider;

    public EmbeddingDevController(EmbeddingProvider embeddingProvider) {
        this.embeddingProvider = embeddingProvider;
    }

    @PostMapping("/embedding")
    public List<Double> generateEmbedding(@RequestBody String text) {

        return embeddingProvider.generateEmbedding(text);

    }

}