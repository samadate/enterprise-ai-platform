package com.sam.enterpriseai.knowledge.chunk;

import com.sam.enterpriseai.config.ChunkProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SentenceAwareChunkingServiceTest {

    private SentenceAwareChunkingService chunkingService;

    @BeforeEach
    void setUp() {
        ChunkProperties properties = new ChunkProperties();
        properties.setMaxSentences(3);

        chunkingService = new SentenceAwareChunkingService(properties);
    }

    @Test
    void shouldReturnEmptyListWhenTextIsNull() {

        List<Chunk> chunks = chunkingService.chunk(null);

        assertThat(chunks).isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenTextIsBlank() {

        List<Chunk> chunks = chunkingService.chunk("   ");

        assertThat(chunks).isEmpty();
    }

    @Test
    void shouldCreateSingleChunkForSingleSentence() {

        String text = "Spring Boot is awesome.";

        List<Chunk> chunks = chunkingService.chunk(text);

        assertThat(chunks).hasSize(1);
        assertThat(chunks.get(0).getContent())
                .isEqualTo("Spring Boot is awesome.");
    }

    @Test
    void shouldCreateSingleChunkWhenSentenceCountEqualsLimit() {

        String text = """
                Sentence one.
                Sentence two.
                Sentence three.
                """;

        List<Chunk> chunks = chunkingService.chunk(text);

        assertThat(chunks).hasSize(1);
    }

    @Test
    void shouldSplitIntoMultipleChunksWhenSentenceLimitExceeded() {

        String text = """
                Sentence one.
                Sentence two.
                Sentence three.
                Sentence four.
                Sentence five.
                Sentence six.
                Sentence seven.
                """;

        List<Chunk> chunks = chunkingService.chunk(text);

        assertThat(chunks).hasSize(3);

        assertThat(chunks.get(0).getContent())
                .contains("Sentence one.")
                .contains("Sentence two.")
                .contains("Sentence three.");

        assertThat(chunks.get(1).getContent())
                .contains("Sentence four.")
                .contains("Sentence five.")
                .contains("Sentence six.");

        assertThat(chunks.get(2).getContent())
                .contains("Sentence seven.");
    }

    @Test
    void shouldIgnoreBlankLinesBetweenSentences() {

        String text = """
                Sentence one.


                Sentence two.



                Sentence three.
                """;

        List<Chunk> chunks = chunkingService.chunk(text);

        assertThat(chunks).hasSize(1);

        assertThat(chunks.get(0).getContent())
                .contains("Sentence one.")
                .contains("Sentence two.")
                .contains("Sentence three.");
    }

    @Test
    void shouldTrimLeadingAndTrailingWhitespace() {

        String text = "   Spring Boot is awesome.   ";

        List<Chunk> chunks = chunkingService.chunk(text);

        assertThat(chunks).hasSize(1);

        assertThat(chunks.get(0).getContent())
                .isEqualTo("Spring Boot is awesome.");
    }
}