package com.example.backend.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Getter
//TODO: Ném ngoại lệ với thông điệp cụ thể và mã trạng thái HTTP
public class AppException extends RuntimeException {
	private final String message;
	private final int status;
	private final String timestamp;
	
	public AppException(String message, HttpStatus status, String timestamp) {
		this.message = message;
		this.status = status.value();
		this.timestamp = Instant.now().toString();
	}
	
	//
	public AppException(String message, HttpStatus status) {
		this(message, status, Instant.now().toString());
	}
}
