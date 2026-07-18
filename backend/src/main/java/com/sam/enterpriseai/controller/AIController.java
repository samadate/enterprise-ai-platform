package com.sam.enterpriseai.controller;

import com.sam.enterpriseai.dto.AIRequest;
import com.sam.enterpriseai.dto.AIResponse;
import com.sam.enterpriseai.service.AIService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/generate")
    public AIResponse generate(@RequestBody AIRequest request) {
        return aiService.generate(request);
    }
}