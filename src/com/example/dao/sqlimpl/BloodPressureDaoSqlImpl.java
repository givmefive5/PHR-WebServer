package com.example.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Repository;

import com.example.dao.BloodPressureDao;
import com.example.exceptions.DataAccessException;
import com.example.model.BloodPressure;

@Repository("bloodPressureDao")
public class BloodPressureDaoSqlImpl extends BaseDaoSqlImpl implements
		BloodPressureDao {

	@Override
	public void addBloodPressure(BloodPressure bloodPressure)
			throws DataAccessException {
		try {
			Connection conn = getConnection();
			String query = "INSERT INTO bloodpressuretracker(systolic, diastolic, datetime, status, userID) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bloodPressure.getSystolic());
			pstmt.setInt(2, bloodPressure.getDiastolic());
			pstmt.setDate(3, bloodPressure.getDateAdded());
			pstmt.setString(5, bloodPressure.getStatus());
			pstmt.setInt(6, bloodPressure.getUserID());

			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
	}

	@Override
	public Integer getIdFromDatabase(BloodPressure bloodPressure)
			throws DataAccessException {

		try {
			Connection conn = getConnection();
			String query = "SELECT id FROM bloodpressuretracker WHERE"
					+ "userID = ?, datetime = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bloodPressure.getUserID());
			pstmt.setDate(2, bloodPressure.getDateAdded());

			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

	}
}
