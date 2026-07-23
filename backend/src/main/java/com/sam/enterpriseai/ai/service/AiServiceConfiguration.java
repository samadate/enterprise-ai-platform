package com.sam.enterpriseai.ai.service;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiServiceConfiguration {

    @Bean
    public EnterpriseAssistant enterpriseAssistant(
            ChatModel chatModel,
            RetrievalAugmentor retrievalAugmentor
    ) {

        return AiServices.builder(EnterpriseAssistant.class)
                .chatModel(chatModel)
                .retrievalAugmentor(retrievalAugmentor)
                .build();
    }
}