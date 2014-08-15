package com.example.exceptions;

public class UserServiceException extends Exception {

	public UserServiceException(String message, Exception e) {
		super(message, e);
	}

}
