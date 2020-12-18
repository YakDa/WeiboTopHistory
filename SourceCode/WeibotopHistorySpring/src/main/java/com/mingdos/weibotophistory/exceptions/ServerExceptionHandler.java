package com.mingdos.weibotophistory.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ServerExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleCommonException(Exception e) {
		Error error = new Error(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(),e.toString());
		return new ResponseEntity<> (error, new HttpHeaders(), error.getStatus());
		
	}
	
}
