package org.cyntho.heimdall.dto;

import lombok.Data;

@Data
public class BotCommandDto {
	private String type;       // z.B. "SEND_CHANNEL_MESSAGE"
	private String target;     // optional: Channel/User
	private String payload;    // Nachricht oder Daten
}