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
				//TODO: JwtAuthFilter là một bộ lọc để xác thực và xử lý JWT
				.addFilterBefore(new JwtAuthFilter(userAuthenticationProvider), BasicAuthenticationFilter.class)
				.csrf(AbstractHttpConfigurer::disable)// vô hiệu hóa CSRF
				.sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// vô hiệu hóa phiên
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers(HttpMethod.POST, "api/v1/login", "/api/v1/register").permitAll()
						.requestMatchers("/api/v1/hello").permitAll()
//						.requestMatchers("/api/v1/user/updateUser").permitAll()
						.requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
						.requestMatchers("/api/v1/user/**").hasAnyRole("USER", "ADMIN")
						.anyRequest().authenticated());
//				.formLogin(withDefaults());
//						.anyRequest().permitAll());// cho phép tất cả
		return http.build();
	}
}
