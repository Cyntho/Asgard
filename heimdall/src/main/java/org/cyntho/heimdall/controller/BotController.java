package org.cyntho.heimdall.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cyntho.heimdall.dto.ChannelInfoDto;
import org.cyntho.heimdall.service.Ts3BotService;
import org.cyntho.heimdall.dto.BotCommandDto;
import org.cyntho.heimdall.dto.BotStatusDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/bot")
@RequiredArgsConstructor
public class BotController {

	private final Ts3BotService botService;

	@PostMapping("/command")
	public void handleCommand(@RequestBody BotCommandDto command) {
		log.info("Receiving /api/bot/command: [{}]", command.toString());
		botService.handleCommand(command);
	}

	@GetMapping("/status")
	public BotStatusDto getStatus() {
		log.info("Receiving /api/bot/status");
		return botService.getStatus();
	}

	@GetMapping("/getChannels")
	public List<ChannelInfoDto> getChannels() {
		log.info("Receiving /api/bot/getChannels");
		return botService.getChannels();
	}
}