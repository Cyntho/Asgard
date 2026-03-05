package org.cyntho.asgard.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cyntho.asgard.dto.AuthDto.*;
import org.cyntho.asgard.service.IAuthService;


import jakarta.servlet.http.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final IAuthService authService;

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request,
	                                             HttpServletRequest httpRequest,
	                                             HttpServletResponse httpResponse) {
		AuthResponse response = authService.register(request, httpRequest, httpResponse);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request,
	                                          HttpServletRequest httpRequest,
	                                          HttpServletResponse httpResponse) {
		log.info("AuthController.PostMapping(/login)");
		AuthResponse response = authService.login(request, httpRequest, httpResponse);
		log.info("--> Reponse = {}", response);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refresh(HttpServletRequest httpRequest,
	                                            HttpServletResponse httpResponse) {
		AuthResponse response = authService.refresh(httpRequest, httpResponse);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest httpRequest,
	                                   HttpServletResponse httpResponse) {
		authService.logout(httpRequest, httpResponse);
		return ResponseEntity.noContent().build();
	}
}
