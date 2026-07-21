package com.sam.enterpriseai.dto;

import jakarta.validation.constraints.NotBlank;

public class IngestionRequest {

    @NotBlank
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
