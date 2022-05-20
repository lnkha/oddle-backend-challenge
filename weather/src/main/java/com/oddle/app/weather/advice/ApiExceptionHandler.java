package com.oddle.app.weather.advice;

import com.oddle.app.weather.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

	private ResponseEntity<ApiError> createError(final HttpStatus httpStatus, final String errorCode) {
		return new ResponseEntity<>(ApiError.builder().status(httpStatus.value()).errorCode(errorCode).build(),
				httpStatus);
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiError> handleUserNotFoundException(final NotFoundException ex) {
		log.error(ex.getMessage(), ex);
		return createError(HttpStatus.NOT_FOUND, ex.getMessage());
	}
}
