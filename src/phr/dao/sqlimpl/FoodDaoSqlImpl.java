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

import phr.dao.FoodDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.tools.ImageHandler;
import phr.web.models.FBPost;
import phr.web.models.Food;
import phr.web.models.FoodTrackerEntry;
import phr.web.models.PHRImage;
import phr.web.models.PHRImageType;

@Repository("foodDao")

public class FoodDaoSqlImpl extends BaseDaoSqlImpl implements FoodDao {
	
	@Autowired
	UserDao userDao;

	@Override
	public int addReturnsEntryID(FoodTrackerEntry foodTrackerEntry) throws DataAccessException {
		
		try {
			Connection conn = getConnection();
			String query = "INSERT INTO foodtracker(FoodID, servingCount, dateAdded, status, userID, fbPostID, photo) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, foodTrackerEntry.getFood().getEntryID());
			pstmt.setDouble(2, foodTrackerEntry.getServingCount());
			pstmt.setTimestamp(3, foodTrackerEntry.getTimestamp());
			pstmt.setString(4, foodTrackerEntry.getStatus());
			pstmt.setInt(5, foodTrackerEntry.getUserID());
			if (foodTrackerEntry.getFbPost() != null)
				pstmt.setInt(6, foodTrackerEntry.getFbPost().getId());
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
	public void edit(FoodTrackerEntry object) throws DataAccessException,
			EntryNotFoundException {
		// TODO Auto-generated method stub
		
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
			String query = "SELECT id, foodID, servingCount, fbPostID status, photo, dateAdded "
					+ " FROM foodList WHERE userID = ? ";

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
						new FBPost(rs.getInt("fbPostID")),
						rs.getTimestamp("dateAdded"),
						rs.getString("status"),
						image, 
						getFood(rs.getInt("foodID")),
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

	@Override
	public int addFoodListEntryReturnEntryID(Food food) throws DataAccessException {
		
		try {
			Connection conn = getConnection();
			String query = "INSERT INTO foodlist(name, calorie, protein, fat, carbohydrate, servingUnit, servingSize, restaurantID, fromFatsecret) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, food.getName());
			pstmt.setDouble(2, food.getCalorie());
			pstmt.setDouble(3, food.getProtein());
			pstmt.setDouble(4, food.getFat());
			pstmt.setDouble(5, food.getCarbohydrate());
			pstmt.setString(6, food.getServingUnit());
			pstmt.setDouble(7, food.getServingSize());
			pstmt.setInt(8, food.getRestaurantID());
			pstmt.setBoolean(9, food.getFromFatsecret());
		
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
	public Boolean foodEntryExists(Food food) throws DataAccessException {
		
		try {
			Connection conn = getConnection();
			String query = "SELECT * FROM foodlist WHERE "
					+ "name = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, food.getName());
			
			ResultSet rs = pstmt.executeQuery();

			if (rs.next())
				return rs.getBoolean(1);
			else
				return null;
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
	}

	@Override
	public List<Food> getAllFood() throws DataAccessException {
		
		List<Food> foods = new ArrayList<Food>();
		
		try{
			Connection conn = getConnection();
			String query = "SELECT id, name, calorie, servingUnit, servingSize, restaurantID, fromFatsecret FROM foodList";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				foods.add(new Food(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getDouble("calorie"),
						rs.getDouble("protein"),
						rs.getDouble("fat"),
						rs.getDouble("carbohydrate"),
						rs.getString("servingUnit"),
						rs.getDouble("servingSize"),
						rs.getInt("restaurantID"),
						rs.getBoolean("fromFatsecret")));	
			}
		}catch (Exception e){
			throw new DataAccessException(
				"An error has occured while trying to access data from the database",
				e);
		}
		
		return foods;
	}

	@Override
	public Food getFood(int entryID) throws DataAccessException {
		Food food = new Food(entryID);
		
		try{
			Connection conn = getConnection();
			String query = "SELECT name, calorie, servingUnit, servingSize, restaurantID, fromFatsecret"
					+ " FROM foodList WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, entryID);
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				food.setName(rs.getString("name"));
				food.setCalorie(rs.getDouble("calorie"));
				food.setServingUnit(rs.getString("servingUnit"));
				food.setServingSize(rs.getDouble("servingSize"));
				food.setRestaurantID(rs.getInt("restaurantID"));
				food.setFromFatsecret(rs.getBoolean("fromFatsecret"));
			}
		}catch (Exception e){
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
			}
		
		return food;
		
	}

	@Override
	public List<Food> search(String searchQuery) {
		// TODO Auto-generated method stub
		return null;
	}

}
