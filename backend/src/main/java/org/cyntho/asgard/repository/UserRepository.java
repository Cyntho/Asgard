package org.cyntho.asgard.repository;

import org.cyntho.asgard.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {


	boolean existsByUsernameIgnoreCase(String username);

	boolean existsByEmailIgnoreCase(String email);

	boolean existsByUsernameIgnoreCaseOrEmailIgnoreCase(String username, String email);

	Optional<UserEntity> findByUsername(String username);

	Optional<UserEntity> findByUsernameIgnoreCaseOrEmailIgnoreCase(String username, String email);
}
