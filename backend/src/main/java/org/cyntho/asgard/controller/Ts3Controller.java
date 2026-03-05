package org.cyntho.asgard.controller;

import lombok.RequiredArgsConstructor;
import org.cyntho.asgard.bot.BotClient;
import org.cyntho.asgard.bot.BotEventStore;
import org.cyntho.asgard.bot.dto.BotCommand;
import org.cyntho.asgard.bot.dto.BotEvent;
import org.cyntho.asgard.bot.dto.BotStatus;
import org.cyntho.asgard.bot.dto.ChannelInfoDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/ts3")
@RequiredArgsConstructor
public class Ts3Controller {

	private final BotEventStore eventStore;
	private final BotClient botClient;

	@GetMapping("/events")
	public List<BotEvent> getEvents() {
		return new ArrayList<>(eventStore.drainEvents());
	}

	@PostMapping("/command")
	public void sendCommand(@RequestBody BotCommand command) {
		botClient.sendCommand(command);
	}

	@GetMapping("/status")
	public BotStatus getStatus() {
		return botClient.getStatus();
	}

	@GetMapping("/channel")
	public List<ChannelInfoDto> getChannels() {return botClient.getChannels().block();}
}
