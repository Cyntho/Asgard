package org.cyntho.asgard.dto;

import org.cyntho.asgard.user.UserEntity;

public class AuthDto {

	public record LoginRequest(String username, String password) {}
	public record RegisterRequest(String username, String email, String password) {}

	public record AuthResponse(String accessToken, UserEntity user) {}
}
