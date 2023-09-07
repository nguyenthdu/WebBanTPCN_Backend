package com.example.backend.config;

import com.example.backend.dtos.ErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
//TODO: thành phần quản lý bởi Spring IoC (Inversion of Control)
@Component
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,//Đối tượng đại diện cho yêu cầu HTTP đang được xử lý.
            HttpServletResponse response,//Đối tượng đại diện cho phản hồi HTTP được tạo ra để trả về thông báo lỗi.
            AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);// Đặt mã trạng thái HTTP của phản hồi thành 401 (Unauthorized) để chỉ ra rằng yêu cầu không được xác thực.
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);//Phản hồi daạng json
        OBJECT_MAPPER.writeValue(response.getOutputStream(), new ErrorDto("Unauthorized path"));// Sử dụng một đối tượng ObjectMapper để chuyển đối tượng ErrorDto thành dữ liệu JSON và ghi vào đầu ra của phản hồi.
    }
}
