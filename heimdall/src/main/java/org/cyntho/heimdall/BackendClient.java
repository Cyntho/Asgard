package org.cyntho.heimdall;

import lombok.RequiredArgsConstructor;
import org.cyntho.heimdall.dto.BotEventDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class BackendClient {

	private final WebClient backendWebClient;

	public void sendEvent(BotEventDto event) {
		backendWebClient.post()
				.uri("/api/bot/events")
				.bodyValue(event)
				.retrieve()
				.toBodilessEntity()
				.block();
	}
}