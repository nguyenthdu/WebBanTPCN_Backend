package com.example.backend.dtos;
//TODO: DTO được sử dụng để truyền thông tin từ người dùng khi họ đăng nhập.
public record CredentialsDto (String username, char[] password) { }