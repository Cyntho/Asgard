package org.cyntho.heimdall.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class BotConfig {

	@Value("${BOT_DISPLAY_NAME}")
	private String botDisplayName;

	@Value("${BOT_QUERY_PASS}")
	private String botPassword;

	@Value("${BOT_SERVER_HOST}")
	private String botHost;

	@Value("${BOT_SERVER_PORT}")
	private int botPort;

	@Value("${BOT_QUERY_NAME}")
	private String botQueryName;

	@Value("${BOT_RECONNECT_ATTEMPTS}")
	private int reconnectAttempts;
}