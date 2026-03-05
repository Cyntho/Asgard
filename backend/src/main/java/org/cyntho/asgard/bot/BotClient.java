package org.cyntho.asgard.bot;

import lombok.RequiredArgsConstructor;
import org.cyntho.asgard.bot.dto.BotCommand;
import org.cyntho.asgard.bot.dto.BotStatus;
import org.cyntho.asgard.bot.dto.ChannelInfoDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BotClient {

	private final WebClient botWebClient;

	public void sendCommand(BotCommand command) {
		botWebClient.post()
				.uri("/api/bot/command")
				.bodyValue(command)
				.retrieve()
				.toBodilessEntity()
				.block();
	}

	public BotStatus getStatus() {
		return botWebClient.get()
				.uri("/api/bot/status")
				.retrieve()
				.bodyToMono(BotStatus.class)
				.block();
	}

	public Mono<List<ChannelInfoDto>> getChannels() {
		return botWebClient.get()
				.uri("/api/bot/channels")
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<>() {
				});
	}
}
