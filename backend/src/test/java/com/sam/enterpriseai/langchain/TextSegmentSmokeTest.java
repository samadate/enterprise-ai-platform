package com.sam.enterpriseai.langchain;

import dev.langchain4j.data.segment.TextSegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextSegmentSmokeTest {

    @Test
    void shouldCreateTextSegment() {

        TextSegment segment =
                LangChainTextSegments.from("Enterprise AI");

        assertEquals(
                "Enterprise AI",
                segment.text());

    }

}