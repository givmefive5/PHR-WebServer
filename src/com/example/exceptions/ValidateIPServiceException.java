package com.example.exceptions;

public class ValidateIPServiceException extends Exception {
	public ValidateIPServiceException(String message, Exception e){
		super(message, e);
	}
}
