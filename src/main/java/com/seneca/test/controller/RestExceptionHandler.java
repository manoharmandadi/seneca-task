package com.seneca.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.seneca.test.exceptions.ValidationException;
import com.seneca.test.model.ErrorResponse;

@ControllerAdvice
public class RestExceptionHandler {

	@RequestMapping(produces = { "application/json", "application/xml" })
	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorResponse handleBadRequestException(ValidationException ex) throws Exception {

		return constructErrorResponse(ex.getCode(), ex.getMessage());
	}

	private ErrorResponse constructErrorResponse(int code, String message) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCode(code);
		errorResponse.setMessage(message);
		return errorResponse;
	}
	
	
	
}
