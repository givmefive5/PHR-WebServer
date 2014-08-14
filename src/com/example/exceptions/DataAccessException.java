package com.example.exceptions;

public class DataAccessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4250137713004333641L;

	public DataAccessException(String message, Exception e){
		super(message, e);
	}
	
}
