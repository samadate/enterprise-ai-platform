package com.sam.enterpriseai.ai.rag;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.injector.DefaultContentInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoggingContentInjector extends DefaultContentInjector {

    private static final Logger log =
            LoggerFactory.getLogger(LoggingContentInjector.class);

    @Override
    protected Prompt createPrompt(ChatMessage chatMessage,
                                  List<Content> contents) {

        Prompt prompt = super.createPrompt(chatMessage, contents);

        log.info("==========================================");
        log.info("Augmented Prompt");
        log.info("{}", prompt.text());
        log.info("==========================================");

        return prompt;
    }
}