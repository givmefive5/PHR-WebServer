package com.example.dao;

import java.sql.Timestamp;

import com.example.exceptions.DataAccessException;

public interface ValidateIPDao {
	
	public int countIPRecords(String ip) throws DataAccessException;

	public Timestamp getLatestIPRecordDate(String ip) throws DataAccessException;
	
	public void addIPEntry(String ip, Timestamp timestamp) throws DataAccessException;

	public void clearAllIPRecords(String ip) throws DataAccessException;
}
