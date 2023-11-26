package com.example.backend.services;

import com.example.backend.dtos.CredentialsDto;
import com.example.backend.dtos.SignUpDto;
import com.example.backend.dtos.UserDto;
import com.example.backend.entities.User_;

public interface UserService {
	UserDto login(CredentialsDto credentialsDto);
	
	UserDto register(SignUpDto userDto);
	
	User_ findByUsername(String username);
	
	UserDto findByLogin(String login);
	
	UserDto updateUser(UserDto userDto);
	
	UserDto adminUpdateUser(UserDto userDto, String password);
	
	void deleteUser(Long id);
}
