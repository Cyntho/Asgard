package org.cyntho.asgard.service;
// package: com.example.demo.security.jwt

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

	private final String secretKey;
	private final long accessTokenExpirationMs;
	private final long refreshTokenExpirationMs;

	public JwtService(
			@Value("${jwt.secret}") String secretKey,
			@Value("${jwt.access-token-expiration-ms}") long accessTokenExpirationMs,
			@Value("${jwt.refresh-token-expiration-ms}") long refreshTokenExpirationMs
	) {
		this.secretKey = secretKey;
		this.accessTokenExpirationMs = accessTokenExpirationMs;
		this.refreshTokenExpirationMs = refreshTokenExpirationMs;
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateAccessToken(UserDetails userDetails) {
		return generateToken(Map.of("type", "access"), userDetails, accessTokenExpirationMs);
	}

	public String generateRefreshToken(UserDetails userDetails) {
		return generateToken(Map.of("type", "refresh"), userDetails, refreshTokenExpirationMs);
	}

	private String generateToken(Map<String, Object> extraClaims,
	                             UserDetails userDetails,
	                             long expirationMs) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + expirationMs);

		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(now)
				.setExpiration(expiry)
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> resolver) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		return resolver.apply(claims);
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	public boolean isTokenExpired(String token) {
		Date exp = extractClaim(token, Claims::getExpiration);
		return exp.before(new Date());
	}

	public String extractTokenType(String token) {
		return extractClaim(token, claims -> claims.get("type", String.class));
	}
}
