package com.example.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.dao.BloodPressureDao;
import com.example.dao.UserDao;
import com.example.exceptions.DataAccessException;
import com.example.model.BloodPressure;
import com.example.tools.EncryptionHandler;

@Repository("bloodPressureDao")
public class BloodPressureDaoSqlImpl extends BaseDaoSqlImpl implements
		BloodPressureDao {

	@Autowired
	UserDao userDao;

	@Override
	public void addBloodPressure(String username, BloodPressure bloodPressure)
			throws DataAccessException {
		try {
			Connection conn = getConnection();
			String query = "INSERT INTO bloodpressure(systolic, diastolic, date, time, status, userID) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt;

			String encryptedSystolic = EncryptionHandler.encrypt(new Integer(
					bloodPressure.getSystolic()).toString());
			String encryptedDiastolic = EncryptionHandler.encrypt(new Integer(
					bloodPressure.getDiastolic()).toString());
			String encryptedDate = EncryptionHandler.encrypt(new Integer(
					bloodPressure.getDate()).toString());
			String encryptedTime = EncryptionHandler.encrypt(new Integer(
					bloodPressure.getTime()).toString());
			String encryptedStatus = EncryptionHandler.encrypt(new Integer(
					bloodPressure.getStatus()).toString());

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, encryptedSystolic);
			pstmt.setString(2, encryptedDiastolic);
			pstmt.setString(3, encryptedDate);
			pstmt.setString(4, encryptedTime);
			pstmt.setString(5, encryptedStatus);

			pstmt.setInt(6, userDao.getUserIdGivenUsername(username));

			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
	}

}
