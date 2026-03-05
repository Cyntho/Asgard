package org.cyntho.heimdall.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BackendWebClientConfig {

	@Value("${backend.base-url}")
	private String backendBaseUrl;

	@Bean
	public WebClient backendWebClient() {
		return WebClient.builder()
				.baseUrl(backendBaseUrl)
				.build();
	}
}