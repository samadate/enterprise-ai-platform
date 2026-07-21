package com.sam.enterpriseai.config;

import com.sam.enterpriseai.constants.BeanNames;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    @Qualifier(BeanNames.OLLAMA_REST_CLIENT)
    public RestClient ollamaRestClient(
            RestClient.Builder builder,
            ChatProperties chatProperties) {

        return builder
                .baseUrl(chatProperties.getBaseUrl())
                .build();
    }
}