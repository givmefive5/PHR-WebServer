package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import phr.dao.BloodPressureDao;
import phr.exceptions.DataAccessException;
import phr.web.models.BloodPressure;

@Repository("bloodPressureDao")
public class BloodPressureDaoSqlImpl extends BaseDaoSqlImpl implements
		BloodPressureDao {

	@Override
	public void add(BloodPressure bloodPressure) throws DataAccessException {
		try {
			Connection conn = getConnection();
			String query = "INSERT INTO bloodpressuretracker(systolic, diastolic, dateAdded, status, userID) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bloodPressure.getSystolic());
			pstmt.setInt(2, bloodPressure.getDiastolic());
			pstmt.setDate(3, bloodPressure.getDateAdded());
			pstmt.setString(4, bloodPressure.getStatus());
			pstmt.setInt(5, bloodPressure.getUserID());

			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
	}

	@Override
	public void edit(BloodPressure object) throws DataAccessException {
		// find entry with id object.getEntryID();
		// Set all fields of that entry to the values in the object.
		// Throw EntryNotFoundException if the entryID was not found from DB
	}

	@Override
	public void delete(BloodPressure object) throws DataAccessException {
		// find entry with id object.getEntryID();
		// Delete that entry from db.
		// Throw EntryNotFoundException if the entryID was not found from DB
	}

	@Override
	public BloodPressure get(Integer entryID) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<BloodPressure> getAll(String username)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getEntryId(BloodPressure bloodPressure)
			throws DataAccessException {

		try {
			Connection conn = getConnection();
			String query = "SELECT id FROM bloodpressuretracker WHERE "
					+ "userID = ? AND dateAdded = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bloodPressure.getUserID());
			pstmt.setDate(2, bloodPressure.getDateAdded());

			ResultSet rs = pstmt.executeQuery();

			if (rs.next())
				return rs.getInt(1);
			else
				return null;
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

	}

}
