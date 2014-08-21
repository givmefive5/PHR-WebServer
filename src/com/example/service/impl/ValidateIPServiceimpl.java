package com.example.service.impl;

import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.ValidateIPDao;
import com.example.dao.sqlimpl.BaseDaoSqlImpl;
import com.example.exceptions.DataAccessException;
import com.example.exceptions.ValidateIPServiceException;
import com.example.service.ValidateIPService;
import com.example.tools.TimestampHandler;

@Service("validateIPService")
public class ValidateIPServiceimpl extends BaseDaoSqlImpl implements ValidateIPService {

	@Autowired
	ValidateIPDao validateIPDao;

	@Override
	public boolean isValidIP(String ip) throws DataAccessException,
			ValidateIPServiceException {
		int count = validateIPDao.countIPRecords(ip);
	
		try {
			if (count < 5){
				return true;
			}
			else {
				Timestamp timestamp = validateIPDao.getLatestIPRecordDate(ip);
				timestamp.setTime(timestamp.getTime() + (60*60*1000));
				
				if(TimestampHandler.getCurrentTimestamp().after(timestamp))
				{
					clearAllIPRecords(ip);
					return true;
				} else {
					// validateIPDao.addIPEntry(ip, new Date());
					return false;
				}
			}
		} catch (DataAccessException e) {
			throw new DataAccessException(
					"An error occured while validating ip", e);
		}
	}

	@Override
	public void addIPEntry(String ip, Timestamp timestamp) throws DataAccessException {
		try {
			Connection conn = getConnection();
			String query = "INSERT INTO validateIp(ip, date) VALUES (?, ?)";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, ip);
			pstmt.setTimestamp(2, timestamp);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
		
	}

	@Override
	public void clearAllIPRecords(String ip) throws DataAccessException {
		try {
			Connection conn = getConnection();
			String query = "DELETE FROM validateip WHERE ip = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, ip);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
		
	}
	
	
}
