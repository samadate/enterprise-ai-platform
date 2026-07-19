package com.sam.enterpriseai.dto.ollama;

public record OllamaGenerateRequest(

        String model,

        String prompt,

        boolean stream

) {
}