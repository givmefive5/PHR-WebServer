package com.example.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.example.dao.LogDao;
import com.example.exceptions.DataAccessException;
import com.example.model.Log;

@Repository("logDao")
public class LogDaoSqlImpl extends BaseDaoSqlImpl implements LogDao {

	@Override
	public void addLog(Log log) throws DataAccessException {
		if (log != null) {
			try {
				Connection conn = getConnection();
				String query = "INSERT INTO logs(message, ip, time, loggingLayer) VALUES (?, ?, ?, ?)";
				PreparedStatement pstmt;

				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, log.getMessage());
				pstmt.setString(2, log.getIp());
				pstmt.setTimestamp(3, log.getTimestamp());
				pstmt.setString(4, log.getLoggingLayer());

				pstmt.executeUpdate();

			} catch (SQLException e) {
				throw new DataAccessException(
						"An error has occured while trying to access data from the database",
						e);
			}
		}

	}

}
