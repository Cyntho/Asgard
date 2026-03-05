package org.cyntho.asgard.bot.dto;

import lombok.Data;

@Data
public class BotEvent {
	private String type;       // z.B. "TEXT_MESSAGE"
	private String message;    // Nachricht
	private String user;       // User-Name/ID
	private Long timestamp;    // epoch millis
}
