package com.ownbank.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.ownbank.shared.dto.UserDto;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user);

}
