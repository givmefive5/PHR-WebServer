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

import phr.dao.FoodDao;
import phr.dao.FoodTrackerDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.models.FBPost;
import phr.models.Food;
import phr.models.FoodTrackerEntry;
import phr.models.PHRImage;
import phr.models.PHRImageType;
import phr.tools.ImageHandler;

@Repository("foodTrackerDao")

public class FoodTrackerDaoSqlImpl extends BaseDaoSqlImpl implements FoodTrackerDao {
	
	//@Autowired
	//UserDao userDao;
	
	//@Autowired
	//FoodDao foodDao;
	
	UserDao userDao = new UserDaoSqlImpl();
	FoodDao foodDao = new FoodDaoSqlImpl();

	@Override
	public int addReturnsEntryID(FoodTrackerEntry foodTrackerEntry) throws DataAccessException {
		
		try {
			Connection conn = getConnection();
			String query = "INSERT INTO foodtracker(FoodID, servingCount, dateAdded, status, userID, facebookID, photo) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, foodTrackerEntry.getFood().getEntryID());
			pstmt.setDouble(2, foodTrackerEntry.getServingCount());
			pstmt.setTimestamp(3, foodTrackerEntry.getTimestamp());
			pstmt.setString(4, foodTrackerEntry.getStatus());
			pstmt.setInt(5, foodTrackerEntry.getUserID());
			if (foodTrackerEntry.getFacebookID() != null)
				pstmt.setString(6, foodTrackerEntry.getFacebookID());
			else
				pstmt.setNull(6, Types.NULL);
			if (foodTrackerEntry.getImage() != null) {
				String encodedImage = foodTrackerEntry.getImage()
						.getEncodedImage();
				String fileName = ImageHandler
						.saveImage_ReturnFilePath(encodedImage);
				foodTrackerEntry.getImage().setFileName(fileName);
				pstmt.setString(7, foodTrackerEntry.getImage().getFileName());
			}
			else
				pstmt.setNull(7, Types.NULL);

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
	public void edit(FoodTrackerEntry foodTrackerEntry) throws DataAccessException,
			EntryNotFoundException {
		
		try{	
			Connection conn = getConnection();
			String query = "UPDATE foodtracker SET foodID = ?, servingCount = ?, dateAdded =? , status = ?, photo = ?) "
					+ "WHERE id = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, foodTrackerEntry.getFood().getEntryID());
			pstmt.setDouble(2, foodTrackerEntry.getServingCount());
			pstmt.setTimestamp(3, foodTrackerEntry.getTimestamp());
			pstmt.setString(4, foodTrackerEntry.getStatus());
			if (foodTrackerEntry.getImage() != null) {
				String encodedImage = foodTrackerEntry.getImage()
						.getEncodedImage();
				String fileName = ImageHandler
						.saveImage_ReturnFilePath(encodedImage);
				foodTrackerEntry.getImage().setFileName(fileName);
				pstmt.setString(5, foodTrackerEntry.getImage().getFileName());
			}
			else
				pstmt.setNull(5, Types.NULL);
			
			pstmt.setInt(6, foodTrackerEntry.getEntryID());
			
			pstmt.executeUpdate();
			
		}catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
	}

	@Override
	public void delete(FoodTrackerEntry object) throws DataAccessException,
			EntryNotFoundException {
		
		try {
			Connection conn = getConnection();
			String query = "DELETE FROM foodtracker WHERE id = ?";

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
	public List<FoodTrackerEntry> getAll(String userAccessToken)
			throws DataAccessException {
		
		List<FoodTrackerEntry> foods = new ArrayList<FoodTrackerEntry>();
		
		try{
			Connection conn = getConnection();
			String query = "SELECT id, foodID, servingCount, facebookID status, photo, dateAdded "
					+ " FROM foodtracker WHERE userID = ? ";

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

				foods.add(new FoodTrackerEntry(
						rs.getInt("id"),
						rs.getString("facebookID"),
						rs.getTimestamp("dateAdded"),
						rs.getString("status"),
						image, 
						foodDao.getFood(rs.getInt("foodID")),
						rs.getDouble("servingCount")
						));	
			}
		}catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
		
		return foods;
	}

	@Override
	public Integer getEntryId(FoodTrackerEntry foodTrackerEntry)
			throws DataAccessException {
		try {
			Connection conn = getConnection();
			String query = "SELECT id FROM foodtracker WHERE "
					+ "userID = ? AND dateAdded = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, foodTrackerEntry.getUserID());
			pstmt.setTimestamp(2, foodTrackerEntry.getTimestamp());

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
