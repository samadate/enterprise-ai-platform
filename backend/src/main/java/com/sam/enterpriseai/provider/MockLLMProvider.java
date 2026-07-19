package com.sam.enterpriseai.provider;

import com.sam.enterpriseai.constants.AIProviders;
import com.sam.enterpriseai.dto.AIRequest;
import com.sam.enterpriseai.dto.AIResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = "ai",
        name = "provider",
        havingValue = AIProviders.MOCK
)
public class MockLLMProvider implements LLMProvider {

    @Override
    public AIResponse generate(AIRequest request) {

        return new AIResponse(
                "Mock response for prompt: " + request.prompt()
        );

    }
}