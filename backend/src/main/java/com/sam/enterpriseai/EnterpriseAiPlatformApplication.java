package com.sam.enterpriseai;

import com.sam.enterpriseai.config.ChatProperties;
import com.sam.enterpriseai.config.ChunkProperties;
import com.sam.enterpriseai.config.EmbeddingProperties;
import com.sam.enterpriseai.config.VectorStoreProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties({ChatProperties.class,
        EmbeddingProperties.class,
        ChunkProperties.class,
        VectorStoreProperties.class})
public class EnterpriseAiPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnterpriseAiPlatformApplication.class, args);
	}

}
