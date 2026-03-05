package org.cyntho.heimdall.dto;

import lombok.Data;

@Data
public class BotStatusDto {
	private boolean connected;
	private String nickname;
}