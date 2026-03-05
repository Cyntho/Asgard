package org.cyntho.asgard.user;


import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.time.Instant;


@Getter
@Setter
@Entity
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID", nullable = false)
	private Long userId;

	@Column(name = "DISPLAY_NAME", nullable = false, unique = true)
	private String username;

	@Column(name = "EMAIL", nullable = false, unique = true)
	private String email;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "CREATED_AT", nullable = false)
	private Instant createdAt;

	@Enumerated(EnumType.STRING)
	private UserRole role;

	private boolean enabled = true;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(role);
	}

	@Override
	@NullMarked
	public boolean isAccountNonExpired() { return true; }

	@Override
	@NullMarked
	public boolean isAccountNonLocked() { return true; }

	@Override
	@NullMarked
	public boolean isCredentialsNonExpired() { return true; }
}


