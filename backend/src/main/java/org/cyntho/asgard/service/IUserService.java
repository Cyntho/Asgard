package org.cyntho.asgard.service;

import org.cyntho.asgard.dto.AuthDto;
import org.cyntho.asgard.user.UserEntity;

public interface IUserService {

	UserEntity registerUser(AuthDto.RegisterRequest request);

	UserEntity getUserByUsername(String username);

}
