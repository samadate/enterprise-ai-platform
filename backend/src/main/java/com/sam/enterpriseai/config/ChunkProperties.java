package com.sam.enterpriseai.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix="ai.chunk")
public class ChunkProperties {

    @NotNull
    private Integer maxSentences;

    public Integer getMaxSentences() {
        return maxSentences;
    }

    public void setMaxSentences(Integer maxSentences) {
        this.maxSentences = maxSentences;
    }
}
