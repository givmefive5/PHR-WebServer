package com.example.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.stereotype.Repository;

import com.example.dao.ValidateIPDao;
import com.example.exceptions.DataAccessException;

@Repository("validateIPDao")
public class ValidateIPDaoImpl extends BaseDaoSqlImpl implements ValidateIPDao {

	@Override
	public int countIPRecords(String ip) throws DataAccessException {
		int count = 0;
		
		try {
			Connection conn = getConnection();
			String query = "SELECT COUNT(*) FROM validateip "
					+ "WHERE ip = ?";
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
	public Date getLatestIPRecordDate(String ip) throws DataAccessException {
		
		Date date;
		try {
			Connection conn = getConnection();
			String query = "SELECT date FROM validateip WHERE ip = ? "
					+ "ORDER BY date DESC LIMIT 1";
			
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, ip);

			ResultSet rs = pstmt.executeQuery();

			rs.next();
			date = rs.getDate(1);
			pstmt.close();
			conn.close();
		} catch (SQLException | DataAccessException e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
		return null;
	}

	@Override
	public void addIPEntry(String ip, Date date) throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			Connection conn = getConnection();
			String query = "INSERT INTO validateIp(ip, date) VALUES (?, ?)";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, ip);
			pstmt.setDate(2, (java.sql.Date) date);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
		
	}

	@Override
	public void clearAllIPRecords(String ip) throws DataAccessException {
		// TODO Auto-generated method stub
		
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
