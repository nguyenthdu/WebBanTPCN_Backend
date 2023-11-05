package com.example.backend.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend.dtos.UserDto;
import com.example.backend.entities.Roles;
import com.example.backend.services.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

@RequiredArgsConstructor
@Component
public class UserAuthenticationProvider {
	private final UserService userService;
	//Đây là một trường dùng để đọc giá trị của khóa bí mật từ tệp cấu hình Spring Boot. Nếu không có giá trị nào được cung cấp trong tệp cấu hình, mặc định là "secret-key".
	@Value("${security.jwt.token.secret-key:secret-key}")
	private String secretKey;
	
	@PostConstruct
	protected void init() {
		// this is to avoid having the raw secret key available in the JVM
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	// tạo một JWT dựa trên thông tin từ đối tượng UserDto
	public String createToken(UserDto user) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + 3600000); // 1 hour
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		//Tạo thông tin đi kèm với token
		return JWT.create()
				.withSubject(user.getUsername())//xác thực tên người dùng
				.withIssuedAt(now)// thời điểm có token có hiệu lực
				.withExpiresAt(validity)// thời điểm hết hiệu lực
				.withClaim("id", user.getId())
				.withClaim("firstName", user.getFirstName())
				.withClaim("lastName", user.getLastName())
				.withClaim("phone", user.getPhone())
				.withClaim("email", user.getEmail())
				.withClaim("role", user.getRole().name())
				//lây thông tin enable 0 1 từ database
				.withClaim("enabled", user.isEnabled() ? 1 : 0)
				.sign(algorithm);
	}
	//Xác thực jwt  bằng cách sử dụng khóa bí mật đã sử dụng để tạo token
	
	public Authentication validateToken(String token) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		JWTVerifier verifier = JWT.require(algorithm)
				.build();
		DecodedJWT decoded = verifier.verify(token);
		UserDto user = UserDto.builder()
				.username(decoded.getSubject())
				.id(decoded.getClaim("id").asLong())
				.firstName(decoded.getClaim("firstName").asString())
				.lastName(decoded.getClaim("lastName").asString())
				.phone(decoded.getClaim("phone").asString())
				.email(decoded.getClaim("email").asString())
				.role(Roles.valueOf(decoded.getClaim("role").asString())) // Trích xuất role từ token
				.enabled(decoded.getClaim("enabled").asInt() == 1)
				.build();
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
		// trả về đôi tượng
		return new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);
	}
	
	// xác thực jwt, lấy thông tin người dùng tìm người dùng dựa trên tên đăng nhập và trả về đối tượng
	public Authentication validateTokenStrongly(String token) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		JWTVerifier verifier = JWT.require(algorithm)
				.build();
		DecodedJWT decoded = verifier.verify(token);
		UserDto user = userService.findByLogin(decoded.getSubject());
		return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
	}
}
