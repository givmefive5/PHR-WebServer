package com.example.service;

import java.sql.Timestamp;

import com.example.exceptions.DataAccessException;
import com.example.exceptions.ValidateIPServiceException;


public interface ValidateIPService {
	
	public boolean isValidIP(String ip) throws DataAccessException, ValidateIPServiceException;

	public void addIPEntry(String ip, Timestamp timestamp) throws DataAccessException;
	
	public void clearAllIPRecords(String ip) throws DataAccessException;
}
