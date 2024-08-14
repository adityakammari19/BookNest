package com.cts.gateway.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends Exception {

	private HttpStatus status;
	private String message;
	public InvalidTokenException(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
}
