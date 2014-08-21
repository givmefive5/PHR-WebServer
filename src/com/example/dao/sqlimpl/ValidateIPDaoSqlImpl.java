package com.example.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.stereotype.Repository;

import com.example.dao.ValidateIPDao;
import com.example.exceptions.DataAccessException;

@Repository("validateIPDao")
public class ValidateIPDaoSqlImpl extends BaseDaoSqlImpl implements
		ValidateIPDao {

	@Override
	public int countIPRecords(String ip) throws DataAccessException {
		int count = 0;

		try {
			Connection conn = getConnection();
			String query = "SELECT COUNT(*) FROM validateip " + "WHERE ip = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, ip);

			ResultSet rs = pstmt.executeQuery();

			rs.next();
			count = rs.getInt(1);
			pstmt.close();
			conn.close();
		} catch (SQLException | DataAccessException e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

		return count;
	}

	@Override
	public Timestamp getLatestIPRecordDate(String ip)
			throws DataAccessException {

		Timestamp timestamp;
		try {
			Connection conn = getConnection();
			String query = "SELECT date FROM validateip WHERE ip = ? "
					+ "ORDER BY date DESC LIMIT 1";

			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, ip);

			ResultSet rs = pstmt.executeQuery();

			rs.next();
			timestamp = rs.getTimestamp(1);
			pstmt.close();
			conn.close();
		} catch (SQLException | DataAccessException e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
		return timestamp;
	}

	@Override
	public void addIPEntry(String ip, Timestamp timestamp)
			throws DataAccessException {
		// TODO Auto-generated method stub

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
