package org.cyntho.asgard.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cyntho.asgard.dto.AuthDto;
import org.cyntho.asgard.service.IAuthService;
import org.cyntho.asgard.service.IUserService;
import org.cyntho.asgard.service.JwtService;
import org.cyntho.asgard.user.UserEntity;
import jakarta.servlet.http.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final IUserService userService;


	@Override
	public AuthDto.AuthResponse register(AuthDto.RegisterRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		try {
			UserEntity user = userService.registerUser(request);

			String accessToken = jwtService.generateAccessToken(user);
			String refreshToken = jwtService.generateRefreshToken(user);
			addRefreshTokenCookie(httpResponse, refreshToken);

			return new AuthDto.AuthResponse(accessToken);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@Override
	public AuthDto.AuthResponse login(AuthDto.LoginRequest request,
	                                  HttpServletRequest httpRequest,
	                                  HttpServletResponse httpResponse) {

		log.info("AuthServiceImpl.login({}, {})", request.username(), request.password());
		Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.username(),
						request.password()
				)
		);

		log.info("AuthServiceImpl.login: auth principal = {}", auth.getPrincipal());

		UserEntity user = (UserEntity) auth.getPrincipal();

		String accessToken = jwtService.generateAccessToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);
		addRefreshTokenCookie(httpResponse, refreshToken);

		log.info("AuthServiceImpl.login: Generated token {}", accessToken);
		return new AuthDto.AuthResponse(accessToken);
	}

	@Override
	public AuthDto.AuthResponse refresh(HttpServletRequest httpRequest,
	                                    HttpServletResponse httpResponse) {

		String refreshToken = extractRefreshTokenFromCookies(httpRequest);
		if (refreshToken == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No refresh token");
		}

		try {
			String tokenType = jwtService.extractTokenType(refreshToken);
			if (!"refresh".equals(tokenType)) {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token type");
			}

			String username = jwtService.extractUsername(refreshToken);
			UserEntity user = userService.getUserByUsername(username);

			if (!jwtService.isTokenValid(refreshToken, user)) {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
			}

			String newAccessToken = jwtService.generateAccessToken(user);
			String newRefreshToken = jwtService.generateRefreshToken(user);
			addRefreshTokenCookie(httpResponse, newRefreshToken);

			return new AuthDto.AuthResponse(newAccessToken);
		} catch (ResponseStatusException e) {
			throw e;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
		}
	}

	@Override
	public void logout(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		deleteRefreshTokenCookie(httpResponse);
	}

	/* Helper */
	private void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
		Cookie cookie = new Cookie("refreshToken", refreshToken);
		cookie.setHttpOnly(true);
		cookie.setSecure(false); // PROD: true (HTTPS!) #ToDo
		cookie.setPath("/api/auth");
		cookie.setMaxAge(14 * 24 * 60 * 60); // 14 Tage
		response.addCookie(cookie);
	}

	private void deleteRefreshTokenCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie("refreshToken", "");
		cookie.setHttpOnly(true);
		cookie.setSecure(false); // PROD: true #ToDo
		cookie.setPath("/api/auth");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

	private String extractRefreshTokenFromCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) return null;

		for (Cookie cookie : cookies) {
			if ("refreshToken".equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}
}
