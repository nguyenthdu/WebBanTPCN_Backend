package com.example.backend.config;

import com.example.backend.dtos.ErrorDto;
import com.example.backend.exceptions.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//TODO:xử lý các ngoại lệ trong ứng dụng và trả về phản hồi HTTP phù hợp.
@ControllerAdvice//thành phần xử lý ngoại lệ toàn cục trong ứng dụng
public class RestExceptionHandler {
	@ExceptionHandler(value = {AppException.class})
	@ResponseBody
	public ResponseEntity<ErrorDto> handleException(AppException ex) {
		//thiết lập mã trạng thái HTTP của phản hồi dựa trên mã trạng thái của AppException thông qua ex.getStatus()
		//-> trả về http phù hợp với lỗi
		return ResponseEntity.status(ex.getStatus())
				.body(new ErrorDto(ex.getMessage(), ex.getStatus(), ex.getTimestamp()));//chứa thông tin phản hồi
	}
}