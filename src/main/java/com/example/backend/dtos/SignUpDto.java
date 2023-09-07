package com.example.backend.dtos;
//TODO: DTO được sử dụng để truyền thông tin từ người dùng khi họ đăng ký tài khoản.

public record SignUpDto (String firstName, String lastName, String phone, String email,String username, char[] password) { }
