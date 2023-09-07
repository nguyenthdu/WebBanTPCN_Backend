package com.example.backend.exceptions;

import org.springframework.http.HttpStatus;
//TODO: Ném ngoại lệ với thông điệp cụ thể và mã trạng thái HTTP
public class AppException extends RuntimeException {

    private final HttpStatus status;

    public AppException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
