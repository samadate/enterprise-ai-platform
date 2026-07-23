package com.sam.enterpriseai.ai.chat;

import com.sam.enterpriseai.ai.constants.AIProviders;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ChatProviders {

    @Bean
    @ConditionalOnProperty(
            prefix = "ai.chat",
            name = "provider",
            havingValue = AIProviders.OLLAMA,
            matchIfMissing = true
    )
    public ChatModel ollamaChatModel(ChatProperties properties) {

        return OllamaChatModel.builder()
                .baseUrl(properties.getBaseUrl())
                .modelName(properties.getModel())
                .timeout(Duration.ofSeconds(120))
                .logRequests(true)
                .logResponses(true)
                .build();
    }
}