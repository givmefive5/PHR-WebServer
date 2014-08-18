package com.example.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.ValidateIPDao;
import com.example.exceptions.DataAccessException;
import com.example.exceptions.ValidateIPServiceException;
import com.example.service.ValidateIPService;

@Service("validateIPService")
public class ValidateIPServiceimpl implements ValidateIPService {

	@Autowired
	ValidateIPDao validateIPDao;
	
	
	private void clearAllIPRecords(String ip) throws ValidateIPServiceException{
		
		try {
			validateIPDao.clearAllIPRecords(ip);
		} catch (DataAccessException e) {
			throw new ValidateIPServiceException(
					"An error occured while clearing ip records", e);
		}
	}
	
	@Override
	public boolean isValidIP(String ip) throws DataAccessException, ValidateIPServiceException {
		int count = validateIPDao.countIPRecords(ip);
		
		try {
			if(count < 5)
				return true;
			else{
				Date date = validateIPDao.getLatestIPRecordDate(ip);
				if(new Date().compareTo(new Date(date.getTime() + 60 * 60 * 1000)) > 0){
				    clearAllIPRecords(ip);
					return true;
					}
				else{
					//validateIPDao.addIPEntry(ip, new Date());
					return false;
				}
			}
		} catch (DataAccessException e) {
			throw new DataAccessException (
					"An error occured while validating ip", e);
		}
	}
}
