package com.sam.enterpriseai.service;

import com.sam.enterpriseai.constants.LogMessages;
import com.sam.enterpriseai.dto.AIRequest;
import com.sam.enterpriseai.dto.AIResponse;
import com.sam.enterpriseai.knowledge.rag.RAGService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AIService {

    private static final Logger log =
            LoggerFactory.getLogger(AIService.class);

    private final RAGService ragService;

    public AIService(RAGService ragService) {
        this.ragService = ragService;
    }

    public AIResponse generate(AIRequest request) {

        long start = System.currentTimeMillis();

        log.info(LogMessages.AI_REQUEST_RECEIVED);

        try {

            AIResponse response =
                    ragService.generate(request);

            long duration =
                    System.currentTimeMillis() - start;

            log.info(
                    LogMessages.AI_REQUEST_COMPLETED,
                    duration
            );

            return response;

        } catch (Exception ex) {

            long duration =
                    System.currentTimeMillis() - start;

            log.error(
                    LogMessages.AI_REQUEST_FAILED,
                    duration,
                    ex
            );

            throw ex;
        }
    }
}