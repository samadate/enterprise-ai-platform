package com.sam.enterpriseai.transition;

import com.sam.enterpriseai.knowledge.embedding.EmbeddedChunk;
import dev.langchain4j.data.segment.TextSegment;

public final class EmbeddedChunkAdapter {

    private EmbeddedChunkAdapter() {
    }

    public static TextSegment toTextSegment(
            EmbeddedChunk chunk) {

        return TextSegment.from(
                chunk.getContent());

    }

}