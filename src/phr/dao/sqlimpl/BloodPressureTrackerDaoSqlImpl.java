package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import phr.dao.BloodPressureTrackerDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.models.BloodPressure;
import phr.models.FBPost;
import phr.models.PHRImage;
import phr.models.PHRImageType;
import phr.tools.ImageHandler;

@Repository("bloodPressureTrackerDao")
public class BloodPressureTrackerDaoSqlImpl extends BaseDaoSqlImpl implements
		BloodPressureTrackerDao {

	//@Autowired
	//UserDao userDao;
	
	UserDao userDao = new UserDaoSqlImpl();

	@Override
	public int addReturnsEntryID(BloodPressure bloodPressure)
			throws DataAccessException {
		try {
			Connection conn = getConnection();
			String query = "INSERT INTO bloodpressuretracker(systolic, diastolic, dateAdded, status, userID, facebookID, photo) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, bloodPressure.getSystolic());
			pstmt.setInt(2, bloodPressure.getDiastolic());
			pstmt.setTimestamp(3, bloodPressure.getTimestamp());
			pstmt.setString(4, bloodPressure.getStatus());
			pstmt.setInt(5, bloodPressure.getUserID());
			if (bloodPressure.getFacebookID() != null)
				pstmt.setString(6, bloodPressure.getFacebookID());
			else
				pstmt.setNull(6, Types.NULL);
			
			if(bloodPressure.getImage()!= null){
				String encodedImage = bloodPressure.getImage().getEncodedImage();
				String fileName = ImageHandler
						.saveImage_ReturnFilePath(encodedImage);
				bloodPressure.getImage().setFileName(fileName);
				pstmt.setString(7, bloodPressure.getImage().getFileName());
			}
			else
				pstmt.setString(7, null);
			
			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();

			int entryID = -1;
			if (rs.next())
				entryID = rs.getInt(1);

			return entryID;

		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
	}

	@Override
	public void edit(BloodPressure bloodPressure) throws DataAccessException,
			EntryNotFoundException {
		try {
			Connection conn = getConnection();
			String query = "UPDATE bloodpressuretracker SET systolic = ?, diastolic = ?, dateAdded = ?, status=?, photo=?"
					+ " WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bloodPressure.getSystolic());
			pstmt.setInt(2, bloodPressure.getDiastolic());
			pstmt.setTimestamp(3, bloodPressure.getTimestamp());
			pstmt.setString(4, bloodPressure.getStatus());
			if(bloodPressure.getImage()!= null)
			{
				String encodedImage = bloodPressure.getImage().getEncodedImage();
				String fileName = ImageHandler
						.saveImage_ReturnFilePath(encodedImage);
				bloodPressure.getImage().setFileName(fileName);
				pstmt.setString(5, bloodPressure.getImage().getFileName());
			}
			else
				pstmt.setNull(5, Types.NULL);
			pstmt.setInt(6, bloodPressure.getEntryID());

			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new EntryNotFoundException(
					"Object ID not found in the database", e);
		}
	}

	@Override
	public void delete(BloodPressure bloodPressure) throws DataAccessException,
			EntryNotFoundException {
		try {
			Connection conn = getConnection();
			String query = "DELETE FROM bloodpressuretracker WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bloodPressure.getEntryID());

			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new EntryNotFoundException(
					"Object ID not found in the database", e);
		}
	}

	@Override
	public List<BloodPressure> getAll(String userAccessToken)
			throws DataAccessException {

		List<BloodPressure> bloodpressures = new ArrayList<BloodPressure>();
		
		try {
			Connection conn = getConnection();
			String query = "SELECT id, facebookID, systolic, diastolic, status, photo, dateAdded FROM bloodpressuretracker WHERE userID = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, userDao.getUserIDGivenAccessToken(userAccessToken));

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				PHRImage image = null;
				if(rs.getString("photo") == null)
					image = null;
				else{
					String encodedImage = ImageHandler.getEncodedImageFromFile(rs.getString("photo"));
					image = new PHRImage(encodedImage, PHRImageType.IMAGE);
				}
				bloodpressures.add(new BloodPressure(
						rs.getInt("id"),
						rs.getString("facebookID"), 
						rs.getTimestamp("dateAdded"), 
						rs.getString("status"), 
						image, 
						rs.getInt("systolic"), 
						rs.getInt("diastolic")));
			}
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

		return bloodpressures;
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
