package org.cyntho.heimdall.service;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventType;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.exception.TS3CommandFailedException;
import com.github.theholywaffle.teamspeak3.api.exception.TS3ConnectionFailedException;
import com.github.theholywaffle.teamspeak3.api.reconnect.ConnectionHandler;
import com.github.theholywaffle.teamspeak3.api.reconnect.ReconnectStrategy;
import com.github.theholywaffle.teamspeak3.api.wrapper.Channel;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cyntho.heimdall.BackendClient;
import org.cyntho.heimdall.dto.BotCommandDto;
import org.cyntho.heimdall.dto.BotEventDto;
import org.cyntho.heimdall.dto.BotStatusDto;
import org.cyntho.heimdall.config.BotConfig;
import org.cyntho.heimdall.dto.ChannelInfoDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class Ts3BotService {

	private final BotConfig botConfig;
	private final BackendClient backendClient;

	private TS3Query query;
	private TS3Api api;
	private volatile int clientId;
	private volatile boolean connected;

	@PostConstruct
	public void start() {
		log.info("Starting TS3 bot...");
		try {
			TS3Config config = buildTs3Config();
			query = new TS3Query(config);

			log.info("Connecting to {}:{} credentials: user=[{}], pass=[{}]",
					botConfig.getBotHost(),
					botConfig.getBotPort(),
					botConfig.getBotQueryName(),
					botConfig.getBotPassword());

			try {
				query.connect();
			} catch (Exception any){
				log.error("Unexpected error: {}", any.getMessage());
			}

			if (query.isConnected()){
				log.info("Connection successful!");
			} else {
				log.error("Unable to connect.. Something went wrong!");
				stop();
				return;
			}

			api = query.getApi();
			stuffThatOnlyEverNeedsToBeRunOnce(api);
			connected = true;
		} catch (Exception e){
			log.error("Unable to connect to teamspeak server.");
			log.error(e.getMessage());
			for (StackTraceElement element:
			     e.getStackTrace()) {
				log.error(element.toString());
			}
		}
	}

	@PreDestroy
	public void stop() {
		log.info("Stopping TS3 bot...");
		if (query != null) {
			query.exit();
		}
	}

	private TS3Config buildTs3Config() throws RuntimeException {
		TS3Config config = new TS3Config();
		config.setHost(botConfig.getBotHost());
		config.setQueryPort(botConfig.getBotPort());
		config.setEnableCommunicationsLogging(true);
		config.setReconnectStrategy(ReconnectStrategy.exponentialBackoff());
		config.setConnectionHandler(new ConnectionHandler() {
			@Override
			public void onConnect(TS3Api api) {
				log.info("TS3 bot connected");
				connected = true;
				try {
					stuffThatNeedsToRunEveryTimeTheQueryConnects(api);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}

			@Override
			public void onDisconnect(TS3Query ts3Query) {
				log.warn("TS3 bot disconnected");
				connected = false;
			}
		});
		return config;
	}

	private void stuffThatNeedsToRunEveryTimeTheQueryConnects(TS3Api api) throws InterruptedException{
		api.login(botConfig.getBotQueryName(), botConfig.getBotPassword());
		api.selectVirtualServerById(1);

		api.setNickname(botConfig.getBotDisplayName());
		api.registerEvent(TS3EventType.TEXT_CHANNEL, 0);
		clientId = api.whoAmI().getId();

		api.addTS3Listeners(new TS3EventAdapter() {
			@Override
			public void onTextMessage(TextMessageEvent e) {
				if (e.getTargetMode() == TextMessageTargetMode.CHANNEL && e.getInvokerId() != clientId) {
					BotEventDto event = new BotEventDto();
					event.setType("TEXT_MESSAGE");
					event.setMessage(e.getMessage());
					event.setUser(e.getInvokerName());
					event.setTimestamp(System.currentTimeMillis());
					backendClient.sendEvent(event);
				}
			}
		});
	}

	private void stuffThatOnlyEverNeedsToBeRunOnce(TS3Api api) {
		api.sendChannelMessage(botConfig.getBotDisplayName() + " is online.");
	}

	public void handleCommand(BotCommandDto command) {
		if (api == null) {
			log.warn("API not initialized, cannot handle command");
			return;
		}
		switch (command.getType()) {
			case "SEND_CHANNEL_MESSAGE" -> api.sendChannelMessage(command.getPayload());
			default -> log.warn("Unknown command type: {}", command.getType());
		}
	}

	public BotStatusDto getStatus() {
		BotStatusDto status = new BotStatusDto();
		status.setConnected(connected);
		status.setNickname(botConfig.getBotDisplayName());
		return status;
	}

	public List<ChannelInfoDto> getChannels(){
		List<ChannelInfoDto> result = new ArrayList<>();

		if (!connected || api == null) {return result;}

		api.getChannels().forEach(c -> {
			ChannelInfoDto dto = new ChannelInfoDto();
			dto.setId(c.getId());
			dto.setName(c.getName());
			dto.setParent(c.getParentChannelId());
		});

		return result;
	}
}
