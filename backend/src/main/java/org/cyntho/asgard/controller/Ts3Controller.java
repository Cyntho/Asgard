package org.cyntho.asgard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cyntho.asgard.bot.BotClient;
import org.cyntho.asgard.bot.BotEventStore;
import org.cyntho.asgard.bot.dto.BotCommand;
import org.cyntho.asgard.bot.dto.BotEvent;
import org.cyntho.asgard.bot.dto.BotStatus;
import org.cyntho.asgard.bot.dto.ChannelInfoDto;
import org.cyntho.asgard.dto.AuthDto;
import org.cyntho.asgard.dto.TsDto;
import org.cyntho.asgard.entity.TsServerConfigConnection;
import org.cyntho.asgard.repository.TsServerConfigConnectionRepository;
import org.cyntho.asgard.repository.UserRepository;
import org.cyntho.asgard.service.ITeamspeakService;
import org.cyntho.asgard.service.IUserService;
import org.cyntho.asgard.service.impl.TeamspeakServiceImpl;
import org.cyntho.asgard.user.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/ts")
@RequiredArgsConstructor
public class Ts3Controller {

	private final BotEventStore eventStore;
	private final BotClient botClient;

	private final TeamspeakServiceImpl teamspeakService;

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
		log.info("Received /status request");
		return botClient.getStatus();
	}

	@GetMapping("/channel")
	public List<ChannelInfoDto> getChannels() {return botClient.getChannels().block();}

	@GetMapping("/configurations")
	public ResponseEntity<List<TsDto.TsServerConnectionDto>> getConfigurations(){
		return ResponseEntity.ok(teamspeakService.getAllConnections());
	}

	@GetMapping("/configurations/:id")
	public ResponseEntity<TsDto.TsServerConnectionDto> getConfiguration(@RequestParam long id){
		return ResponseEntity.ok(teamspeakService.getServerConnection(id));
	}

	@GetMapping("/initTest")
	public void initTest(){
		if (teamspeakService.TestInitialize()){
			log.info("Initialized basic config");
		} else {
			log.error("Unable to initialize basic ts config, probably because it was done before..");
		}
	}


}
