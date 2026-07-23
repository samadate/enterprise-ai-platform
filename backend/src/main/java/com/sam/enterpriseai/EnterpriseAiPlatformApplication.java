package com.sam.enterpriseai;

import com.sam.enterpriseai.ai.chat.ChatProperties;
import com.sam.enterpriseai.ai.vectorstore.PgVectorProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties({
        ChatProperties.class,
        PgVectorProperties.class
})
public class EnterpriseAiPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnterpriseAiPlatformApplication.class, args);
	}

}
