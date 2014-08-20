package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.LogDao;
import com.example.exceptions.DataAccessException;
import com.example.exceptions.LoggingException;
import com.example.model.Log;
import com.example.service.LogService;

@Service("logService")
public class LogServiceImpl implements LogService {

	@Autowired
	LogDao logDao;

	@Override
	public void addLog(Log log) throws LoggingException {
		try {
			logDao.addLog(log);
		} catch (DataAccessException e) {
			throw new LoggingException("Error in logging", e);
		}
	}

}
