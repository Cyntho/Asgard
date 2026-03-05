package org.cyntho.asgard.config;// package: com.example.demo.security.jwt

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.cyntho.asgard.service.JwtService;
import org.cyntho.asgard.service.impl.UserServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserServiceImpl userService;

	public JwtAuthenticationFilter(JwtService jwtService,
	                               UserServiceImpl userService) {
		this.jwtService = jwtService;
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");
		String jwt = null;
		String username = null;

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			jwt = authHeader.substring(7);
			try {
				username = jwtService.extractUsername(jwt);
			} catch (Exception e) {
				// ungültiges Token, wir lassen weiterlaufen (kein Auth-Context)
			}
		}

		if (username != null &&
				SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = userService.loadUserByUsername(username);

			if (jwtService.isTokenValid(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken authToken =
						new UsernamePasswordAuthenticationToken(
								userDetails,
								null,
								userDetails.getAuthorities()
						);
				authToken.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request)
				);
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}
}
