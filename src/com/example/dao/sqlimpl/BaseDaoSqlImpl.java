package com.example.dao.sqlimpl;

import java.sql.Connection;

import com.example.exceptions.DataAccessException;

public abstract class BaseDaoSqlImpl {

	protected Connection getConnection() throws DataAccessException {
		SqlConnectionManager connManager = SqlConnectionManager.getInstance();
		Connection conn = connManager.getConnection();
		return conn;
	}
}
