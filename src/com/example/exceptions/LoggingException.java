package com.example.exceptions;

public class LoggingException extends Exception {

	public LoggingException(String message, Exception e) {
		super(message, e);
	}
}
