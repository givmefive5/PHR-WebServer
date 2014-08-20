package com.example.service;

import com.example.exceptions.LoggingException;
import com.example.model.Log;

public interface LogService {

	public void addLog(Log log) throws LoggingException;

}
