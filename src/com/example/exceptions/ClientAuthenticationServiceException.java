package com.example.exceptions;

public class ClientAuthenticationServiceException extends Exception {

	public ClientAuthenticationServiceException(String message, Exception e) {
		super(message, e);
	}

}
