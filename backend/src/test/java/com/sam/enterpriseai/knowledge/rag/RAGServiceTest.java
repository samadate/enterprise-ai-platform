package com.sam.enterpriseai.knowledge.rag;

import com.sam.enterpriseai.dto.AIRequest;
import com.sam.enterpriseai.dto.AIResponse;
import com.sam.enterpriseai.knowledge.embedding.EmbeddedChunk;
import com.sam.enterpriseai.knowledge.retrieval.RetrievalService;
import com.sam.enterpriseai.provider.llm.LLMProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RAGServiceTest {

    @Mock
    private RetrievalService retrievalService;

    @Mock
    private PromptBuilder promptBuilder;

    @Mock
    private LLMProvider llmProvider;

    @InjectMocks
    private RAGService ragService;

    @Test
    void shouldGenerateGroundedAnswer() {

        AIRequest request =
                new AIRequest("What is Spring Boot?");

        List<EmbeddedChunk> chunks =
                List.of(
                        new EmbeddedChunk(
                                "Spring Boot simplifies Java applications.",
                                List.of(1.0, 2.0)
                        )
                );

        String prompt =
                "Generated Prompt";

        AIResponse expected =
                new AIResponse(
                        "Spring Boot is a Java framework."
                );

        when(retrievalService.retrieve(request.prompt()))
                .thenReturn(chunks);

        when(promptBuilder.buildPrompt(
                request.prompt(),
                chunks))
                .thenReturn(prompt);

        when(llmProvider.generate(any()))
                .thenReturn(expected);

        AIResponse response =
                ragService.generate(request);

        assertEquals(
                expected.response(),
                response.response()
        );

        ArgumentCaptor<AIRequest> captor =
                ArgumentCaptor.forClass(AIRequest.class);

        verify(llmProvider)
                .generate(captor.capture());

        assertEquals(
                prompt,
                captor.getValue().prompt()
        );

        verify(retrievalService)
                .retrieve(request.prompt());

        verify(promptBuilder)
                .buildPrompt(request.prompt(), chunks);

        verifyNoMoreInteractions(
                retrievalService,
                promptBuilder,
                llmProvider
        );
    }
}