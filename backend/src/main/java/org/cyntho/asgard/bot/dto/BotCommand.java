package org.cyntho.asgard.bot.dto;

import lombok.Data;

@Data
public class BotCommand {
	private String type;       // z.B. "SEND_CHANNEL_MESSAGE"
	private String target;     // optional: Channel/User
	private String payload;    // Nachricht oder Daten
}
