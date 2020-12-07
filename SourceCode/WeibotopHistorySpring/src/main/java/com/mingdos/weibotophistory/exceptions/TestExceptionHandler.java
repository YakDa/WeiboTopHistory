package com.mingdos.weibotophistory.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class TestExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(TestException.class)
	protected ResponseEntity<Object> excepton(TestException e) {
		String error = e.toString();
		return new ResponseEntity<> (error, HttpStatus.BAD_REQUEST);
		
	}
	
}
