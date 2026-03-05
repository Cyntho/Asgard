package org.cyntho.asgard.bot.dto;

import lombok.Data;

@Data
public class BotStatus {
	private boolean connected;
	private String nickname;
}
