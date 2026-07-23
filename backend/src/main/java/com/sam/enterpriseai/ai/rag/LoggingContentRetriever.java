package com.sam.enterpriseai.ai.rag;

import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoggingContentRetriever implements ContentRetriever {

    private static final Logger log =
            LoggerFactory.getLogger(LoggingContentRetriever.class);

    private final ContentRetriever delegate;

    public LoggingContentRetriever(ContentRetriever delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<Content> retrieve(Query query) {

        log.info("==========================================");
        log.info("ContentRetriever Invoked");
        log.info("Query : {}", query.text());

        List<Content> contents = delegate.retrieve(query);

        log.info("Retrieved Segments : {}", contents.size());

        for (int i = 0; i < contents.size(); i++) {
            log.info("Segment {}:", i + 1);
            log.info(contents.get(i).textSegment().text());
        }

        log.info("==========================================");

        return contents;
    }
}