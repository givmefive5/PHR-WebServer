package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import phr.dao.UserDao;
import phr.dao.WeightTrackerDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.models.FBPost;
import phr.models.PHRImage;
import phr.models.PHRImageType;
import phr.models.User;
import phr.models.Weight;
import phr.tools.ImageHandler;

@Repository("weightTrackerDao")
public class WeightTrackerDaoSqlImpl extends BaseDaoSqlImpl implements WeightTrackerDao {

	//@Autowired
	//UserDao userDao;
	
	UserDao userDao = new UserDaoSqlImpl();

	@Override
	public int addReturnsEntryID(Weight weight) throws DataAccessException {

		try {
			Connection conn = getConnection();
			String query = "INSERT INTO weighttracker(weightInPounds, dateAdded, status, userID, fbPostID, photo) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setDouble(1, weight.getWeightInPounds());
			pstmt.setTimestamp(2, weight.getTimestamp());
			pstmt.setString(3, weight.getStatus());
			pstmt.setInt(4, weight.getUserID());
			
			if (weight.getFacebookID() != null)
				pstmt.setString(5, weight.getFacebookID());
			else
				pstmt.setNull(5, Types.NULL);

			if (weight.getImage() != null) {
				String encodedImage = weight.getImage().getEncodedImage();
				String fileName = ImageHandler
						.saveImage_ReturnFilePath(encodedImage);
				weight.getImage().setFileName(fileName);
				pstmt.setString(6, weight.getImage().getFileName());
			}
			else
				pstmt.setNull(6, Types.NULL);


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
	public void edit(Weight weight) throws DataAccessException,
			EntryNotFoundException {
		try {
			Connection conn = getConnection();
			String query = "UPDATE weighttracker SET weightInPounds = ?, dateAdded = ?, status = ?, photo = ? "
					+ "WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setDouble(1, weight.getWeightInPounds());
			pstmt.setTimestamp(2, weight.getTimestamp());
			pstmt.setString(3, weight.getStatus());
			if (weight.getImage() != null) {
				String encodedImage = weight.getImage()
						.getEncodedImage();
				String fileName = ImageHandler
						.saveImage_ReturnFilePath(encodedImage);
				weight.getImage().setFileName(fileName);
				pstmt.setString(4, weight.getImage().getFileName());
			}
			else
				pstmt.setNull(4, Types.NULL);
			pstmt.setInt(5, weight.getEntryID());

			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new EntryNotFoundException(
					"Object ID not found in the database", e);
		}

	}

	@Override
	public void delete(Weight object) throws DataAccessException,
			EntryNotFoundException {
		try {
			Connection conn = getConnection();
			String query = "DELETE FROM weighttracker WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, object.getEntryID());

			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new EntryNotFoundException(
					"Object ID not found in the database", e);
		}
	}

	@Override
	public List<Weight> getAll(String userAccessToken)
			throws DataAccessException {

		List<Weight> weights = new ArrayList<Weight>();
		try {
			Connection conn = getConnection();
			String query = "SELECT id, facebookID, weightInPounds, status, photo, dateAdded FROM weighttracker WHERE userID = ?";

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
				weights.add(new Weight(rs.getInt("id"), 
						rs.getString("facebookID"),
						rs.getTimestamp("dateAdded"), 
						rs.getString("status"),
						image, 
						rs.getDouble("weightInPounds")));
			}
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
		return weights;
	}

	@Override
	public Integer getEntryId(Weight weight) throws DataAccessException {

		try {
			Connection conn = getConnection();
			String query = "SELECT id FROM weighttracker WHERE "
					+ "userID = ? AND dateAdded = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, weight.getUserID());
			pstmt.setTimestamp(2, weight.getTimestamp());

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

	@Override
	public Weight getLatestWeight(String userAccessToken) throws DataAccessException {
		
		Weight weight = null;
		
		try {
			Connection conn = getConnection();
			String query = "SELECT * FROM weighttracker WHERE "
					+ "userID = ? ORDER BY id DESC LIMIT 1";
			
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
				
				weight = new Weight(
						rs.getInt("id"), 
						new User(userDao.getUserIDGivenAccessToken(userAccessToken)),
						rs.getString("facebookID"),
						rs.getTimestamp("dateAdded"),
						rs.getString("status"),
						image, 
						rs.getDouble("weightInPounds"));
			}
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
		return weight;
	}

}
