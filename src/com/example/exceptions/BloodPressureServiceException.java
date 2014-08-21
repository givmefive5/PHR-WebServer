package com.example.exceptions;

public class BloodPressureServiceException extends Exception {

	public BloodPressureServiceException(String message, Exception e) {
		super(message, e);
	}
}
