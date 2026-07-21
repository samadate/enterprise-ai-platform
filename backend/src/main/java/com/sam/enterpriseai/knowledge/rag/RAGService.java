package com.sam.enterpriseai.knowledge.rag;

import com.sam.enterpriseai.dto.AIRequest;
import com.sam.enterpriseai.dto.AIResponse;
import com.sam.enterpriseai.knowledge.embedding.EmbeddedChunk;
import com.sam.enterpriseai.knowledge.retrieval.RetrievalService;
import com.sam.enterpriseai.provider.llm.LLMProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RAGService {

    private final RetrievalService retrievalService;
    private final PromptBuilder promptBuilder;
    private final LLMProvider llmProvider;

    public RAGService(
            RetrievalService retrievalService,
            PromptBuilder promptBuilder,
            LLMProvider llmProvider) {

        this.retrievalService = retrievalService;
        this.promptBuilder = promptBuilder;
        this.llmProvider = llmProvider;
    }

    public AIResponse generate(AIRequest request) {

        List<EmbeddedChunk> retrievedChunks =
                retrievalService.retrieve(request.prompt());

        String prompt =
                promptBuilder.buildPrompt(
                        request.prompt(),
                        retrievedChunks
                );

        AIRequest ragRequest =
                new AIRequest(prompt);

        return llmProvider.generate(ragRequest);
    }
}