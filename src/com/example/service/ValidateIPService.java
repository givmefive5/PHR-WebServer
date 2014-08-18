package com.example.service;

import com.example.exceptions.DataAccessException;
import com.example.exceptions.ValidateIPServiceException;


public interface ValidateIPService {
	
	public boolean isValidIP(String ip) throws DataAccessException, ValidateIPServiceException;

}
