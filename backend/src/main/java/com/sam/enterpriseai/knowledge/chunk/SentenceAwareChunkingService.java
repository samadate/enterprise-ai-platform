package com.sam.enterpriseai.knowledge.chunk;

import com.sam.enterpriseai.config.ChunkProperties;
import org.springframework.stereotype.Service;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service
public class SentenceAwareChunkingService implements ChunkingService {

    private final ChunkProperties chunkProperties;

    public SentenceAwareChunkingService(ChunkProperties chunkProperties) {
        this.chunkProperties = chunkProperties;
    }

    @Override
    public List<Chunk> chunk(String text) {

        if (text == null || text.isBlank()) {
            return Collections.emptyList();
        }

        List<String> sentences = extractSentences(text);

        return groupSentences(sentences);

    }

    private List<String> extractSentences(String text) {

        BreakIterator iterator =
                BreakIterator.getSentenceInstance(Locale.ENGLISH);

        iterator.setText(text);

        List<String> sentences = new ArrayList<>();

        int start = iterator.first();

        for (int end = iterator.next();
             end != BreakIterator.DONE;
             start = end, end = iterator.next()) {

            String sentence = text.substring(start, end).trim();

            if (!sentence.isEmpty()) {
                sentences.add(sentence);
            }
        }

        return sentences;
    }

    private List<Chunk> groupSentences(List<String> sentences) {

        List<Chunk> chunks = new ArrayList<>();

        int maxSentences = chunkProperties.getMaxSentences();

        StringBuilder builder = new StringBuilder();

        int count = 0;

        for (String sentence : sentences) {

            if (builder.length() > 0) {
                builder.append(System.lineSeparator());
            }

            builder.append(sentence);

            count++;

            if (count == maxSentences) {

                chunks.add(new Chunk(builder.toString()));

                builder.setLength(0);

                count = 0;
            }
        }

        if (builder.length() > 0) {

            chunks.add(new Chunk(builder.toString()));
        }

        return chunks;
    }
}
