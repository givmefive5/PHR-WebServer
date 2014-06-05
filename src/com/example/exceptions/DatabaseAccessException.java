package com.example.exceptions;

public class DatabaseAccessException extends Exception{

	private static final long serialVersionUID = -8649451878804051319L;

	public DatabaseAccessException(String message, Exception e){
		super(message, e);
	}
}
