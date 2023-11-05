package com.example.backend.dtos;

import com.example.backend.entities.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO:  DTO (Data Transfer Object) được sử dụng để chuyển dữ liệu giữa lớp User và các phần khác của ứng dụng.
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
	private Long id;
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private String username;
	private Roles role;
	private boolean enabled;
	private String token;
}
