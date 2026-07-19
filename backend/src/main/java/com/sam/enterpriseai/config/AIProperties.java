package com.sam.enterpriseai.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "ai")
public class AIProperties {

    private String provider;

    @Valid
    private Chat chat = new Chat();

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public static class Chat {

        @NotBlank
        private String model;

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }
    }

    @Valid
    private Ollama ollama = new Ollama();

    public Ollama getOllama() {
        return ollama;
    }

    public void setOllama(Ollama ollama) {
        this.ollama = ollama;
    }

    public static class Ollama {

        @NotBlank
        private String baseUrl;
        @NotBlank
        private String generateEndpoint;

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getGenerateEndpoint() {
            return generateEndpoint;
        }

        public void setGenerateEndpoint(String generateEndpoint) {
            this.generateEndpoint = generateEndpoint;
        }
    }
}
