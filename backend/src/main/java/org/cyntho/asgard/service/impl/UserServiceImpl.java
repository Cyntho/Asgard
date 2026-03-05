package org.cyntho.asgard.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cyntho.asgard.dto.AuthDto;
import org.cyntho.asgard.user.UserEntity;
import org.cyntho.asgard.repository.UserRepository;
import org.cyntho.asgard.service.IUserService;
import org.cyntho.asgard.user.UserRole;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService, UserDetailsService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;


	@Override
	public UserEntity registerUser(AuthDto.RegisterRequest request) {
		userRepository.findByUsername(request.username()).ifPresent(u -> {
			throw new IllegalArgumentException("Username already taken");
		});

		UserEntity user = UserEntity.builder()
				.username(request.username())
				.email(request.email())
				.password(passwordEncoder.encode(request.password()))
				.role(UserRole.ROLE_USER)
				.createdAt(Instant.now())
				.enabled(true)
				.build();

		return userRepository.save(user);
	}

	@Override
	public UserEntity getUserByUsername(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() ->
						new UsernameNotFoundException("User not found: " + username));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return getUserByUsername(username);
	}
}
