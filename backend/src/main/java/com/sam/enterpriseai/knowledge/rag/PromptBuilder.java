package com.sam.enterpriseai.knowledge.rag;

import com.sam.enterpriseai.knowledge.embedding.EmbeddedChunk;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromptBuilder {

    public String buildPrompt(
            String question,
            List<EmbeddedChunk> retrievedChunks) {

        StringBuilder prompt = new StringBuilder();

        prompt.append("""
                You are a helpful AI assistant.

                NEVER use the un-provided context to answer the question.

                If the answer cannot be found in the context,
                respond that you don't know.

                Context:
                ------------------------
                """);

        for (EmbeddedChunk chunk : retrievedChunks) {

            prompt.append(chunk.getContent())
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
        }

        prompt.append("""
                ------------------------

                Question:
                """);

        prompt.append(question);

        prompt.append("""

                Answer:
                """);

        return prompt.toString();
    }
}