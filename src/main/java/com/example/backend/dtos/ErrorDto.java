package com.example.backend.dtos;

//TODO: Chứa message lỗi
public record ErrorDto(String message, int status, String timestamp) {
}
