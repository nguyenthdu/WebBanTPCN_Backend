package com.example.backend.services.impl;

import com.example.backend.config.PasswordConfig;
import com.example.backend.dtos.CredentialsDto;
import com.example.backend.dtos.SignUpDto;
import com.example.backend.dtos.UserDto;
import com.example.backend.entities.Roles;
import com.example.backend.entities.User_;
import com.example.backend.exceptions.AppException;
import com.example.backend.mappers.UserMapper;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
	private final UserRepository userRepository;
	private final PasswordConfig passwordConfig;
	private final UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User_> user_ = userRepository.findByUsername(username);
		if(user_ == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();// tạo một đối tượng GrantedAuthority để lưu trữ vai trò của người dùng
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user_.get().getRole().name()));// thêm vai trò vào đối tượng GrantedAuthority
		return new User(
				user_.get().getUsername(),
				passwordConfig.passwordEncoder().encode(user_.get().getPassword()),
				grantedAuthorities);
	}
	
	public UserDto login(CredentialsDto credentialsDto) {
		User_ user = userRepository.findByUsername(credentialsDto.username())
				.orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
		if(passwordConfig.passwordEncoder().matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword())) {
			UserDto userDto = userMapper.toUserDto(user);// chuyển đổi user thành userDto
			//nhiều vai trò
//			Set<Roles> roles = new HashSet<>();
//			roles.add(user.getRole());
//			userDto.setRoles(roles);
			// 1 vai trò
//			userDto.setId(user.getId());
//			userDto.setRole(user.getRole());
			return userDto;// trả về thông tin user
		}
		throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
	}
	
	@Override
	public UserDto register(SignUpDto userDto) {
		Optional<User_> optionalUser = userRepository.findByUsername(userDto.username());
		if(optionalUser.isPresent()) {
			throw new AppException("Username already exists", HttpStatus.BAD_REQUEST);
		}
		User_ user = userMapper.signUpToUser(userDto);
		user.setRole(Roles.USER);
		user.setEnabled(true);
		user.setPassword(passwordConfig.passwordEncoder().encode(CharBuffer.wrap(userDto.password())));
		User_ savedUser = userRepository.save(user);
		return userMapper.toUserDto(savedUser);
	}
	
	@Override
	public User_ findByUsername(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
	}
	
	@Override
	public UserDto findByLogin(String login) {
		User_ user = userRepository.findByUsername(login)
				.orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
		return userMapper.toUserDto(user);
	}
	//TODO: ----------------- UNFINISHED - NEED TO BE FIXED  - DON'T USE THIS METHOD -----------------
	
	public UserDto updateUser(UserDto userDto) {
		// Lấy thông tin user từ ID
		Optional<User_> user = userRepository.findById(userDto.getId());
//				.orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
		// Cập nhật thông tin từ DTO
		user.get().setFirstName(userDto.getFirstName());
		user.get().setLastName(userDto.getLastName());
		// Lưu lại thông tin đã cập nhật
		User_ updatedUser = userRepository.save(user.get());
		// Chuyển đổi và trả về UserDto đã cập nhật
		return userMapper.toUserDto(updatedUser);
	}
	
	@Override
	public UserDto adminUpdateUser(UserDto userDto, String password) {
		// Lấy thông tin user từ ID
		User_ user = userRepository.findById(userDto.getId())
				.orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
		// Cập nhật thông tin từ DTO
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setUsername(userDto.getUsername());
		user.setRole(userDto.getRole());
		user.setPassword(passwordConfig.passwordEncoder().encode(CharBuffer.wrap(password)));
		// Lưu lại thông tin đã cập nhật
		User_ updatedUser = userRepository.save(user);
		// Chuyển đổi và trả về UserDto đã cập nhật
		return userMapper.toUserDto(updatedUser);
	}
	
	@Override
	public void deleteUser(Long id) {
		// Kiểm tra xem user có tồn tại không
		User_ user = userRepository.findById(id)
				.orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
		// Xóa user
		userRepository.delete(user);
	}
}
