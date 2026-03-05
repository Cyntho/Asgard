package org.cyntho.asgard.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BotWebClientConfig {

	@Value("${bot.service.base-url}")
	private String botServiceBaseUrl;

	@Bean
	public WebClient botWebClient() {
		return WebClient.builder()
				.baseUrl(botServiceBaseUrl)
				.build();
	}
}
