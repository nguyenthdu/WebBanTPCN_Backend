package com.example.backend.controllers;

import com.example.backend.config.UserAuthenticationProvider;
import com.example.backend.dtos.CredentialsDto;
import com.example.backend.dtos.SignUpDto;
import com.example.backend.dtos.UserDto;
import com.example.backend.entities.User_;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class UserController {
	private final UserService userService;
	private final UserAuthenticationProvider userAuthenticationProvider;
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/hello")
	public ResponseEntity<String> hello() {
		return ResponseEntity.ok("Hello world");
	}
	
	//create method with roles USER
	@GetMapping("user/helloUser")
	public ResponseEntity<String> helloUser() {
		return ResponseEntity.ok("Hello user");
	}
	
	@GetMapping("admin/helloAdmin")
	public ResponseEntity<String> helloAdmin() {
		return ResponseEntity.ok("Hello admin");
	}
	
	@GetMapping("admin/getAllUsers")
	List<User_> getAllUsers() {
		return userRepository.findAll();
	}
	
	//TODO: ---------------- LOGIN -----------------
	@PostMapping("/login")
	public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
		UserDto userDto = userService.login(credentialsDto);
		userDto.setToken(userAuthenticationProvider.createToken(userDto));
		return ResponseEntity.ok(userDto);
	}
	//TODO: ----------------- LOGOUT -----------------
	//Status: doing
	//Xử lý logout xóa token
//	@PostMapping("/logout")
//	public ResponseEntity<String> logout() {
//		// Lấy thông tin người dùng đã được xác thực từ SecurityContextHolder
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if(authentication != null && authentication.getPrincipal() instanceof UserDto) {
//			UserDto userDto = (UserDto) authentication.getPrincipal();
//			userAuthenticationProvider.removeToken(userDto);
//		}
//		return ResponseEntity.ok("Logout successfully");
//	}
	
	//TODO: ----------------- REGISTER -----------------
	@PostMapping("/register")
	public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto user) {
		UserDto createdUser = userService.register(user);
		createdUser.setToken(userAuthenticationProvider.createToken(createdUser));
		return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
	}
	
	@GetMapping("user/getUserInfo")
	public ResponseEntity<UserDto> getUserInfo() {
		// Lấy thông tin người dùng đã được xác thực từ SecurityContextHolder
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.getPrincipal() instanceof UserDto) {
			UserDto userDto = (UserDto) authentication.getPrincipal();
			return ResponseEntity.ok(userDto);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	//TODO: ----------------- UNFINISHED - NEED TO BE FIXED  - DON'T USE THIS METHOD -----------------
	
	@PutMapping("/user/updateUser")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
		// Lấy thông tin người dùng đã được xác thực từ SecurityContextHolder
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.getPrincipal() instanceof UserDto) {
			UserDto authenticatedUser = (UserDto) authentication.getPrincipal();
			// Kiểm tra xem người dùng cập nhật thông tin của chính mình
			if(authenticatedUser.getId().equals(userDto.getId())) {
				// Gọi service để cập nhật thông tin người dùng
				UserDto updatedUser = userService.updateUser(userDto);
				return ResponseEntity.ok(updatedUser);
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Không có quyền truy cập
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Chưa xác thực
		}
//		UserDto updatedUser = userService.updateUser(userDto);
//		return ResponseEntity.ok(updatedUser);
	}
	
	//Cập nhật thông tin user
	@PutMapping("admin/adminUpdateUser")
	public ResponseEntity<UserDto> adminUpdateUser(@RequestBody UserDto userDto, String password) {
		UserDto updatedUser = userService.adminUpdateUser(userDto, password);
		return ResponseEntity.ok(updatedUser);
	}
	
	@DeleteMapping("admin/deleteUser/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.ok("User deleted successfully");
	}
}
