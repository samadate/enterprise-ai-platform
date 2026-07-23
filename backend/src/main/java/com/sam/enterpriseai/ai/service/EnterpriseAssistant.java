package com.sam.enterpriseai.ai.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface EnterpriseAssistant {

    @SystemMessage("""
            You are a expert AI assistant.

            NEVER use outside context or information.
            NEVER answer extra,be concise and focused to question.
            NEVER expose or say that you have context or information.           
            If the answer cannot be found in the context,
            respond that you don't know.
            """)
    String chat(@UserMessage String question);
}