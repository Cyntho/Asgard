package org.cyntho.asgard.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.cyntho.asgard.dto.AuthDto;

public interface IAuthService {

	AuthDto.AuthResponse register(AuthDto.RegisterRequest request,
	                              HttpServletRequest httpRequest,
	                              HttpServletResponse httpResponse);

	AuthDto.AuthResponse login(AuthDto.LoginRequest request,
	                           HttpServletRequest httpRequest,
	                           HttpServletResponse httpResponse);

	AuthDto.AuthResponse refresh(HttpServletRequest httpRequest,
	                             HttpServletResponse httpResponse);

	void logout(HttpServletRequest httpRequest,
	            HttpServletResponse httpResponse);
}
