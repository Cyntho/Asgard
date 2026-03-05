package org.cyntho.heimdall.dto;

import lombok.Data;

@Data
public class BotEventDto {
	private String type;       // z.B. "TEXT_MESSAGE"
	private String message;    // Nachricht
	private String user;       // User-Name/ID
	private Long timestamp;    // epoch millis
}