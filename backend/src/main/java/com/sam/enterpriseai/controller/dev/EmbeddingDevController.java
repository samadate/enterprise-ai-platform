package com.sam.enterpriseai.controller.dev;

import com.sam.enterpriseai.knowledge.chunk.Chunk;
import com.sam.enterpriseai.knowledge.embedding.EmbeddedChunk;
import com.sam.enterpriseai.knowledge.embedding.EmbeddingService;
import org.springframework.web.bind.annotation.*;

/**
 * Temporary controller to test the embedding endpoints.
 * */
@RestController
@RequestMapping("/api/v1/dev/embedding")
public class EmbeddingDevController {

    private final EmbeddingService embeddingService;

    public EmbeddingDevController(EmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    @PostMapping
    public EmbeddedChunk embed(@RequestBody String text) {

        Chunk chunk = new Chunk(text);

        return embeddingService.embed(chunk);
    }

}