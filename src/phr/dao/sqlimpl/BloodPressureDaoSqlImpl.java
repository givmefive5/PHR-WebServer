package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import phr.dao.BloodPressureDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.web.models.BloodPressure;

@Repository("bloodPressureDao")
public class BloodPressureDaoSqlImpl extends BaseDaoSqlImpl implements
		BloodPressureDao {

	@Override
	public void add(BloodPressure bloodPressure) throws DataAccessException {
		try {
			Connection conn = getConnection();
			String query = "INSERT INTO bloodpressuretracker(systolic, diastolic, dateAdded, status, userID, fbPostID, photo) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bloodPressure.getSystolic());
			pstmt.setInt(2, bloodPressure.getDiastolic());
			pstmt.setTimestamp(3, bloodPressure.getTimestamp());
			pstmt.setString(4, bloodPressure.getStatus());
			pstmt.setInt(5, bloodPressure.getUserID());
			if (bloodPressure.getFbPost() != null)
				pstmt.setInt(6, bloodPressure.getFbPost().getId());
			else
				pstmt.setInt(6, -1);
			pstmt.setString(7, bloodPressure.getImageFilePath());

			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
	}

	@Override
	public void edit(BloodPressure object) throws DataAccessException, EntryNotFoundException {
		// find entry with id object.getEntryID();
		// Set all fields of that entry to the values in the object.
		// Throw EntryNotFoundException if the entryID was not found from DB
		
		try{
			Connection conn = getConnection();
			String query = "UPDATE bloodpressuretracker SET systolic = ?, diastolic = ?, dateAdded = ?, status=?, photo=?" +
							"WHERE id = ?";
			
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, object.getSystolic());
			pstmt.setInt(2, object.getDiastolic());
			pstmt.setTimestamp(3, object.getTimestamp());
			pstmt.setString(4, object.getStatus());
			pstmt.setString(5, object.getImageFilePath());
			pstmt.setInt(6, object.getEntryID());

			pstmt.executeUpdate();
			
		}catch (Exception e){
			throw new EntryNotFoundException("Object ID not found in the database", e);
		}
	}

	@Override
	public void delete(BloodPressure object) throws DataAccessException, EntryNotFoundException {
		// find entry with id object.getEntryID();
		// Delete that entry from db.
		// Throw EntryNotFoundException if the entryID was not found from DB
		
		try{
			Connection conn = getConnection();
			String query = "DELETE FROM bloodpressuretracker WHERE id = ?";
			
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, object.getEntryID());
			
		}catch (Exception e){
			throw new EntryNotFoundException("Object ID not found in the database", e);
		}
	}

	@Override
	public BloodPressure get(Integer entryID) throws DataAccessException {
		try{
			
			Connection conn = getConnection();
			String query = "SELECT * FROM bloodpressure WHERE id = ?";
			
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, entryID);
			
			
		}catch (Exception e){
			throw new DataAccessException (
					"An error has occured while trying to access data from the database",
					e);
		}
		
		return null;
	}
	
	@Override
	public Integer getUserID(String userAccessToken) throws DataAccessException, EntryNotFoundException {
		try{
			
			Connection conn = getConnection();
			String query = "SELECT id FROM useraccountandinfo WHERE userAccessToken = ?";
			
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userAccessToken);
			
			ResultSet rs = pstmt.executeQuery();

			if (rs.next())
				return rs.getInt(1);
			else
				return null;
			
		}catch (Exception e){
			throw new EntryNotFoundException("Object ID not found in the database", e);
		}
	}

	@Override
	public ArrayList<BloodPressure> getAll(String userAccessToken)
			throws DataAccessException {
		try{
			Connection conn = getConnection();
			String query = "SELECT * FROM bloodpressuretracker WHERE userID = ?";
			
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, getUserID(userAccessToken));
			
			ResultSet rs = pstmt.executeQuery();
			// add result in BloodPressure object then add to the arrayLIst
			
		}catch (Exception e){
			throw new DataAccessException("An error has occured while trying to access data from the database",
					e);
		}
		
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
			pstmt.setTimestamp(2, bloodPressure.getTimestamp());

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
