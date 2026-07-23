package com.sam.enterpriseai.ai.rag;

import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RagConfiguration {

    @Bean
    public RetrievalAugmentor retrievalAugmentor(
            ContentRetriever contentRetriever
    ) {
        return DefaultRetrievalAugmentor.builder()
                .contentRetriever(contentRetriever)
                .contentInjector(new LoggingContentInjector())
                .build();
    }
}