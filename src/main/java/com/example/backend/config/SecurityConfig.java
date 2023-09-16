package com.example.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//TODO: chịu trách nhiệm xác định cách bảo mật và quản lý quyền truy cập trong ứng dụng.
@RequiredArgsConstructor
@Configuration//Đánh dấu lớp này là một lớp cấu hình Spring.
@EnableWebSecurity// Đánh dấu rằng Spring Security sẽ được kích hoạt và sử dụng trong ứng dụng
public class SecurityConfig {

    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    private final UserAuthenticationProvider userAuthenticationProvider;
//Cấu hình cách bảo mật ứng dụng
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling(customizer -> customizer.authenticationEntryPoint(userAuthenticationEntryPoint))
                .addFilterBefore(new JwtAuthFilter(userAuthenticationProvider), BasicAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.POST, "/login", "/register").permitAll()
//                        .anyRequest().authenticated()); // yêu cầu xác thực
                .anyRequest().permitAll());// cho phép tất cả
        return http.build();
    }
}
/*TODO:
* .exceptionHandling(customizer -> customizer.authenticationEntryPoint(userAuthenticationEntryPoint)): Xác định authenticationEntryPoint được sử dụng trong trường hợp không xác thực thành công, trong trường hợp này, nó sẽ sử dụng userAuthenticationEntryPoint.

.addFilterBefore(new JwtAuthFilter(userAuthenticationProvider), BasicAuthenticationFilter.class): Thêm JwtAuthFilter vào chuỗi bộ lọc trước BasicAuthenticationFilter. JwtAuthFilter được sử dụng để xác thực và xử lý JWT trong các yêu cầu HTTP.

.csrf(AbstractHttpConfigurer::disable): Vô hiệu hóa bảo vệ CSRF (Cross-Site Request Forgery). Điều này phù hợp cho ứng dụng RESTful, nơi không cần sử dụng tính năng này.

.sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)): Thiết lập chế độ quản lý phiên làm việc (session) là STATELESS. Điều này đảm bảo rằng không có phiên làm việc nào được tạo và mọi yêu cầu được xử lý độc lập.

.authorizeHttpRequests((requests) -> requests .requestMatchers(HttpMethod.POST, "/login", "/register").permitAll() .anyRequest().authenticated()): Xác định quy tắc cho việc xác thực yêu cầu HTTP. Ở đây, tất cả các yêu cầu POST đến các đường dẫn "/login" và "/register" được cho phép mà không cần xác thực. Các yêu cầu khác phải được xác thực (authenticated()).
*
* */