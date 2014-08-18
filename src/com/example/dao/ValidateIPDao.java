package com.example.dao;

import java.util.Date;

import com.example.exceptions.DataAccessException;

public interface ValidateIPDao {
	
	public int countIPRecords(String ip) throws DataAccessException;

	public Date getLatestIPRecordDate(String ip) throws DataAccessException;
	
	public void addIPEntry(String ip, Date date) throws DataAccessException;

	public void clearAllIPRecords(String ip) throws DataAccessException;
}
