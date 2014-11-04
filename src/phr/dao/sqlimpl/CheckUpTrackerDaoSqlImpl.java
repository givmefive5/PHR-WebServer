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

import phr.dao.CheckUpTrackerDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.models.CheckUp;
import phr.models.FBPost;
import phr.models.PHRImage;
import phr.models.PHRImageType;
import phr.tools.ImageHandler;

@Repository("checkupTrackerDao")
public class CheckUpTrackerDaoSqlImpl extends BaseDaoSqlImpl implements CheckUpTrackerDao {

	//@Autowired
	//UserDao userDao;
	
	UserDao userDao = new UserDaoSqlImpl();

	@Override
	public int addReturnsEntryID(CheckUp checkUp) throws DataAccessException {
		try {
			Connection conn = getConnection();
			String query = "INSERT INTO checkuptracker(purpose, doctorsName, notes, dateAdded, status, userID, facebookID, photo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, checkUp.getPurpose());
			pstmt.setString(2, checkUp.getDoctorsName());
			pstmt.setString(3, checkUp.getNotes());
			pstmt.setTimestamp(4, checkUp.getTimestamp());
			pstmt.setString(5, checkUp.getStatus());
			pstmt.setInt(6, checkUp.getUserID());
			if (checkUp.getFacebookID() != null)
				pstmt.setString(7, checkUp.getFacebookID());
			else
				pstmt.setNull(7, Types.NULL);

			if (checkUp.getImage() != null) {
				String encodedImage = checkUp.getImage().getEncodedImage();
				String fileName = ImageHandler
						.saveImage_ReturnFilePath(encodedImage);
				checkUp.getImage().setFileName(fileName);
				pstmt.setString(8, checkUp.getImage().getFileName());
			}
			else
				pstmt.setNull(8, Types.NULL);
			
			
			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();

			int entryID = -1;
			if (rs.next())
				entryID = rs.getInt(1);

			conn.close();
			return entryID;

		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
	}

	@Override
	public void edit(CheckUp checkUp) throws DataAccessException,
			EntryNotFoundException {
		try {
			Connection conn = getConnection();
			String query = "UPDATE checkuptracker SET purpose = ?, doctorsName = ?, notes = ?,  dateAdded = ?, status=?, photo=? "
					+ "WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, checkUp.getPurpose());
			pstmt.setString(2, checkUp.getDoctorsName());
			pstmt.setString(3, checkUp.getNotes());
			pstmt.setTimestamp(4, checkUp.getTimestamp());
			pstmt.setString(5, checkUp.getStatus());
			if (checkUp.getImage() != null) {
				String encodedImage = checkUp.getImage()
						.getEncodedImage();
				String fileName = ImageHandler
						.saveImage_ReturnFilePath(encodedImage);
				checkUp.getImage().setFileName(fileName);
				pstmt.setString(6, checkUp.getImage().getFileName());
			}
			else
				pstmt.setNull(6, Types.NULL);
			pstmt.setInt(7, checkUp.getEntryID());

			pstmt.executeUpdate();

			conn.close();
		} catch (Exception e) {
			throw new EntryNotFoundException(
					"Object ID not found in the database", e);
		}
	}

	@Override
	public void delete(CheckUp checkUp) throws DataAccessException,
			EntryNotFoundException {
		try {
			Connection conn = getConnection();
			String query = "DELETE FROM checkuptracker WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, checkUp.getEntryID());

			pstmt.executeUpdate();

			conn.close();
		} catch (Exception e) {
			throw new EntryNotFoundException(
					"Object ID not found in the database", e);
		}
	}

	@Override
	public List<CheckUp> getAll(String userAccessToken)
			throws DataAccessException {
		
		List<CheckUp> checkups = new ArrayList<CheckUp>();
		try {
			Connection conn = getConnection();
			String query = "SELECT id, facebookID, purpose, doctorsName, notes, status, photo, dateAdded FROM checkuptracker WHERE userID = ?";

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
				checkups.add(new CheckUp(rs.getInt("id"), rs.getString("faceboookID"), rs.getTimestamp("dateAdded"), rs
						.getString("status"), image, rs.getString("purpose"),
						rs.getString("doctorsName"), rs.getString("notes")));
			}
			conn.close();
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

		return checkups;
	}

	@Override
	public Integer getEntryId(CheckUp checkUp) throws DataAccessException {

		try {
			Connection conn = getConnection();
			String query = "SELECT id FROM checkuptracker WHERE "
					+ "userID = ? AND dateAdded = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, checkUp.getUserID());
			pstmt.setTimestamp(2, checkUp.getTimestamp());

			ResultSet rs = pstmt.executeQuery();

			Integer id = null;
			if (rs.next())
				id = rs.getInt(1);

			conn.close();
			return id;
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
	}

}
