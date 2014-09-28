package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import phr.dao.FoodDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.tools.ImageHandler;
import phr.web.models.Activity;
import phr.web.models.Food;
import phr.web.models.FoodTrackerEntry;

@Repository("foodDao")

public class FoodDaoSqlImpl extends BaseDaoSqlImpl implements FoodDao {
	
	@Autowired
	UserDao userDao;

	@Override
	public void add(FoodTrackerEntry foodTrackerEntry) throws DataAccessException {
		
		try {
			Connection conn = getConnection();
			String query = "INSERT INTO foodtracker(FoodID, servingCount, dateAdded, status, userID, fbPostID, photo) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, foodTrackerEntry.getFood().getEntryID());
			pstmt.setDouble(2, foodTrackerEntry.getServingCount());
			pstmt.setTimestamp(3, foodTrackerEntry.getTimestamp());
			pstmt.setString(4, foodTrackerEntry.getStatus());
			pstmt.setInt(5, foodTrackerEntry.getUserID());
			if (foodTrackerEntry.getFbPost() != null)
				pstmt.setInt(6, foodTrackerEntry.getFbPost().getId());
			else
				pstmt.setInt(6, -1);
			if (foodTrackerEntry.getImage().getFileName() == null) {
				String encodedImage = foodTrackerEntry.getImage()
						.getEncodedImage();
				String fileName = ImageHandler
						.saveImage_ReturnFilePath(encodedImage);
				foodTrackerEntry.getImage().setFileName(fileName);
			}
			pstmt.setString(7, foodTrackerEntry.getImage().getFileName());

			pstmt.executeUpdate();
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
	public ArrayList<FoodTrackerEntry> getAll(String userAccessToken)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
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
	public void addFoodListEntry(Food food) throws DataAccessException {
		
		try {
			Connection conn = getConnection();
			String query = "INSERT INTO foodlist(name, calorie, servingUnit, servingSize, restaurantID, fromFatsecret) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, food.getName());
			pstmt.setDouble(2, food.getCalorie());
			pstmt.setString(3, food.getServingUnit());
			pstmt.setDouble(4, food.getServingSize());
			pstmt.setInt(5, food.getRestaurantID());
			pstmt.setBoolean(6, food.getFromFatsecret());
		
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
		
	}

	@Override
	public Boolean checkFoodEntryInList(Food food) throws DataAccessException {
		
		try {
			Connection conn = getConnection();
			String query = "SELECT fromFatsecret FROM foodlist WHERE "
					+ "id = ? AND name = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, food.getEntryID());
			pstmt.setString(2, food.getName());
			
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
	public ArrayList<Food> getAllFood() throws DataAccessException {
		ArrayList<Food> foods = new ArrayList<Food>();
		
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

}