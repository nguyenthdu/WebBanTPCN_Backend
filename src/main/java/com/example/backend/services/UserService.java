package com.example.backend.services;


import com.example.backend.dtos.CredentialsDto;
import com.example.backend.dtos.SignUpDto;
import com.example.backend.dtos.UserDto;


public interface UserService {

        UserDto login(CredentialsDto credentialsDto);

        UserDto register(SignUpDto userDto);

        UserDto findByLogin(String login);

}
