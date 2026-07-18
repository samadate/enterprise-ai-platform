package com.sam.enterpriseai.provider;

import com.sam.enterpriseai.dto.AIRequest;
import com.sam.enterpriseai.dto.AIResponse;
import org.springframework.stereotype.Component;

@Component
public class MockLLMProvider implements LLMProvider {

    @Override
    public AIResponse generate(AIRequest request) {

        return new AIResponse(
                "Mock response for prompt: " + request.prompt()
        );

    }
}