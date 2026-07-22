package com.sam.enterpriseai.langchain;

import dev.langchain4j.data.segment.TextSegment;

public final class LangChainTextSegments {

    private LangChainTextSegments() {
    }

    public static TextSegment from(String text) {
        return TextSegment.from(text);
    }

}