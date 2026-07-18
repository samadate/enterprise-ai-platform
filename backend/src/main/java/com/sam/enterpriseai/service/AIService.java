package com.sam.enterpriseai.service;

import com.sam.enterpriseai.dto.AIRequest;
import com.sam.enterpriseai.dto.AIResponse;
import com.sam.enterpriseai.provider.LLMProvider;
import org.springframework.stereotype.Service;

@Service
public class AIService {

    private final LLMProvider llmProvider;

    public AIService(LLMProvider llmProvider) {
        this.llmProvider = llmProvider;
    }

    public AIResponse generate(AIRequest request) {
        return llmProvider.generate(request);
    }
}