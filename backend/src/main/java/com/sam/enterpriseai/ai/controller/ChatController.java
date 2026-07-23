package com.sam.enterpriseai.ai.controller;

import com.sam.enterpriseai.ai.dto.ChatRequest;
import com.sam.enterpriseai.ai.dto.ChatResponse;
import com.sam.enterpriseai.ai.service.EnterpriseAssistant;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final EnterpriseAssistant enterpriseAssistant;

    public ChatController(EnterpriseAssistant enterpriseAssistant) {
        this.enterpriseAssistant = enterpriseAssistant;
    }

    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest request) {

        String answer = enterpriseAssistant.chat(request.question());

        return new ChatResponse(answer);
    }
}