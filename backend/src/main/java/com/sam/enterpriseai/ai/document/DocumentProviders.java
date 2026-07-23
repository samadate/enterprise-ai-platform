package com.sam.enterpriseai.ai.document;

import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentByLineSplitter;
import dev.langchain4j.data.document.splitter.DocumentBySentenceSplitter;
import dev.langchain4j.data.document.splitter.DocumentByWordSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DocumentProviders {

    @Bean
    public DocumentSplitter documentSplitter() {

        return new DocumentByLineSplitter(500,50);
    }
}