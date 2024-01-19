package com.br.product.validation.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionGlobalHandler {

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> handleValidationException(ValidationException e) {
		var details = new ExceptionDetails(HttpStatus.EXPECTATION_FAILED.value(), e.getMessage());
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(details);
	}

}
