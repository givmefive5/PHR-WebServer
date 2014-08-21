package com.example.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.ValidateIPDao;
import com.example.dao.sqlimpl.BaseDaoSqlImpl;
import com.example.exceptions.DataAccessException;
import com.example.exceptions.ValidateIPServiceException;
import com.example.service.ValidateIPService;
import com.example.tools.TimestampHandler;

@Service("validateIPService")
public class ValidateIPServiceimpl extends BaseDaoSqlImpl implements
		ValidateIPService {

	@Autowired
	ValidateIPDao validateIPDao;

	@Override
	public boolean isValidIP(String ip) throws ValidateIPServiceException {

		try {
			int count = validateIPDao.countIPRecords(ip);
			if (count < 5) {
				return true;
			} else {
				Timestamp timestamp = validateIPDao.getLatestIPRecordDate(ip);
				timestamp.setTime(timestamp.getTime() + (60 * 60 * 1000));

				if (TimestampHandler.getCurrentTimestamp().after(timestamp)) {
					clearAllIPRecords(ip);
					return true;
				} else {
					return false;
				}
			}
		} catch (DataAccessException e) {
			throw new ValidateIPServiceException(
					"An error occured while validating ip", e);
		}
	}

	@Override
	public void addIPEntry(String ip, Timestamp timestamp)
			throws DataAccessException {
		int count = validateIPDao.countIPRecords(ip);
		if (count <= 5)
			validateIPDao.addIPEntry(ip, timestamp);
	}

	@Override
	public void clearAllIPRecords(String ip) throws DataAccessException {
		validateIPDao.countIPRecords(ip);
	}

}
