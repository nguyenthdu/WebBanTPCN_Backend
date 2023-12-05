package com.example.backend.validators;

import com.example.backend.entities.User_;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {
	public String validateUser(User_ user) {
		//firstname not null and dont have number and length > 2 && length < 20:
		if(user.getFirstName() == null || user.getFirstName().length() < 2 || user.getFirstName().length() > 20) {
			return "First name must be between 2 and 20 characters";
		}
		if(user.getFirstName().matches(".*\\d.*")) {
			return "First name must not contain number";
		}
		//lastname not null and dont have number and length > 2 && length < 20:
		if(user.getLastName() == null || user.getLastName().length() < 2 || user.getLastName().length() > 20) {
			return "Last name must be between 2 and 20 characters";
		}
		if(user.getLastName().matches(".*\\d.*")) {
			return "Last name must not contain number";
		}
		//phone not null and have 10 number and start with 0:
		if(user.getPhone() == null || user.getPhone().length() != 10) {
			return "Phone must be 10 characters";
		}
		if(!user.getPhone().startsWith("0")) {
			return "Phone must start with 0";
		}
		//email not null and must valid email:
		if(user.getEmail() == null || !user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
			return "Email must be valid";
		}
		//username not null and length > 8 && length < 32:
		if(user.getUsername() == null || user.getUsername().length() < 8 || user.getUsername().length() > 32) {
			return "Username must be between 8 and 32 characters";
		}
		//password not null and length > 8 && length < 32 and must have number and special character and character uppercase:
		if(user.getPassword() == null || user.getPassword().length() < 8 || user.getPassword().length() > 32) {
			return "Password must be between 8 and 32 characters";
		}
		if(!user.getPassword().matches(".*\\d.*")) {
			return "Password must contain number";
		}
		if(!user.getPassword().matches(".*[A-Z].*")) {
			return "Password must contain uppercase character";
		}
		if(!user.getPassword().matches(".*[!@#$%^&*].*")) {
			return "Password must contain special character";
		}
		return null;
	}
}
