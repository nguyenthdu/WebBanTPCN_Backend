package com.example.backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
//TODO: Bộ lọc để xác thực và xử lý JWT

//TODO: Bộ lọc để xác thực và xử lý JWT
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {//Đây là một lớp cơ sở (base class) từ Spring Framework, được kế thừa để tạo một bộ lọc sẽ được thực hiện một lần cho mỗi yêu cầu HTTP.
	private final UserAuthenticationProvider userAuthenticationProvider;
	
	//TODO: phương thức chính của bộ lọc, nơi mà xử lý logic xác thực JWT diễn ra.
	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		///api/v1/hello không cần xác thực
		if("/api/v1/hello".equals(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}
//		if("/api/v1/user/updateUser".equals(request.getRequestURI())) {
//			filterChain.doFilter(request, response);
//			return;
//		}
		//Lấy giá trị của header Authorization từ yêu cầu HTTP bằng cách sử dụng request.getHeader(HttpHeaders.AUTHORIZATION).
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(header != null) {
			String[] authElements = header.split(" ");
			//Kiểm tra xem header có tồn tại hay không, và nếu tồn tại, nó sẽ chia thành hai phần dựa trên khoảng trắng, với phần đầu tiên là "Bearer", và phần thứ hai là token JWT thực sự.
			if(authElements.length == 2
					&& "Bearer".equals(authElements[0])) {
				try {
					/*
					 * kiểm tra phương thức HTTP của yêu cầu. Nếu là GET, nó sử dụng userAuthenticationProvider.validateToken để xác thực token. Nếu không phải là GET,
					 * sử dụng userAuthenticationProvider.validateTokenStrongly để xác thực token
					 * */
					//validateToken dùng để xác thực các request không cần xác thực mạnh
					if("GET".equals(request.getMethod())) {
						SecurityContextHolder.getContext().setAuthentication(
								userAuthenticationProvider.validateToken(authElements[1]));
						//validateTokenStrongly dùng để xác thực các request cần xác thực mạnh
					} else {
						SecurityContextHolder.getContext().setAuthentication(
								userAuthenticationProvider.validateTokenStrongly(authElements[1]));
					}
				} catch (RuntimeException e) {
					SecurityContextHolder.clearContext();
					throw e;
				}
			}
		}
		filterChain.doFilter(request, response);
	}
}