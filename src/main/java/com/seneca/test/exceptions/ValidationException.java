package com.seneca.test.exceptions;

public class ValidationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private int code;
	
	public ValidationException(int code, String message) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
