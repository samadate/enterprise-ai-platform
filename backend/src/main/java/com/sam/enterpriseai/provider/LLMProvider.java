package com.sam.enterpriseai.provider;

import com.sam.enterpriseai.dto.AIRequest;
import com.sam.enterpriseai.dto.AIResponse;

public interface LLMProvider {

    AIResponse generate(AIRequest request);

}