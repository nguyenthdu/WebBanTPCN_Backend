package com.example.backend.services.impl;


import com.example.backend.dtos.CredentialsDto;
import com.example.backend.dtos.SignUpDto;
import com.example.backend.dtos.UserDto;
import com.example.backend.entities.User_;
import com.example.backend.exceptions.AppException;
import com.example.backend.mappers.UserMapper;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;
//TODO: xác thực người dùng dựa trên thông tin đăng nhập (login) và mật khẩu (password) từ credentialsDto

    public UserDto login(CredentialsDto credentialsDto) {
        User_ user = userRepository.findByUsername(credentialsDto.username())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword())) {
            return userMapper.toUserDto(user);// nếu hợp lệ trả về thông tin người dùng
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }
//TODO: đăng ký
    public UserDto register(SignUpDto userDto) {
        Optional<User_> optionalUser = userRepository.findByUsername(userDto.username());

        if (optionalUser.isPresent()) {// kiểm tra đã tồn tại username chưa
            throw new AppException("Username already exists", HttpStatus.BAD_REQUEST);
        }

        User_ user = userMapper.signUpToUser(userDto);
        // mã hóa mật khẩu
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.password())));

        User_ savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }
// tìm người dùng theo username
    public UserDto findByLogin(String login) {
        User_ user = userRepository.findByUsername(login)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }
}
