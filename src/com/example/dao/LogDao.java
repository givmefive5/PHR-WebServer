package com.example.dao;

import com.example.exceptions.DataAccessException;
import com.example.model.Log;

public interface LogDao {

	public void addLog(Log log) throws DataAccessException;
}
