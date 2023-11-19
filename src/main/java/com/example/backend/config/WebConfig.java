package com.example.backend.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

/*TODO:
 * thiết lập và kích hoạt Cors trong ứng dụng Spring Boot của bạn,
 * cho phép các yêu cầu từ trang web có nguồn là "http://localhost:4200" truy cập tài nguyên từ ứng dụng
 * */
@Configuration
@EnableWebMvc
public class WebConfig {
	private static final Long MAX_AGE = 3600L;// thời gian tối đa mà trình duyệt có thể lưu trữ các cài đặt CORS trước đó (preflight requests) trong bộ nhớ cache.
	private static final int CORS_FILTER_ORDER = -102;//CorsFilter sẽ được áp dụng trước SpringSecurityFilter (-100) để đảm bảo rằng CORS được xử lý trước khi xác thực bảo mật.
	
	@Bean
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		// cấu hình cors
		config.setAllowCredentials(true);//Cho phép sử dụng Cookie trong yêu cầu CORS (nếu có).
		//config.addAllowedOrigin("http://localhost:4200");// cho phép truy cập từ 1 nguồn cụ thể
		config.setAllowedOriginPatterns(Arrays.asList("*")); // Sử dụng allowedOriginPatterns
		config.setAllowedHeaders(Arrays.asList(// tiêu đề HTTP được phép trong yêu cầu CORS.
				HttpHeaders.AUTHORIZATION,
				HttpHeaders.CONTENT_TYPE,
				HttpHeaders.ACCEPT));
		config.setAllowedMethods(Arrays.asList(//các phương thức HTTP được phép trong yêu cầu CORS.
				HttpMethod.GET.name(),
				HttpMethod.POST.name(),
				HttpMethod.PUT.name(),
				HttpMethod.DELETE.name()));
		config.setMaxAge(MAX_AGE);// thời gian tối đa trình duyệt lưu trữ cài đặt cors
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		//Thiết lập thứ tự của filter (đã được định nghĩa trước đó bằng CORS_FILTER_ORDER) để đảm bảo rằng CorsFilter được xử lý trước.
		bean.setOrder(CORS_FILTER_ORDER);
		return bean;
	}
}
