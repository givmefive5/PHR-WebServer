package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import phr.dao.FoodDao;
import phr.exceptions.DataAccessException;
import phr.models.Food;

@Repository("foodDao")
public class FoodDaoSqlImpl extends BaseDaoSqlImpl implements FoodDao {

	@Override

	public int addReturnEntryID(Food food) throws DataAccessException {
		
		int entryID = foodEntryExists(food);

		if (entryID != -1) {
			
			incrementCountUsed(food);
			
			return entryID;
		} else {

			try {
				Connection conn = getConnection();
				String query = "INSERT INTO foodlist(name, calorie, protein, fat, carbohydrate, servingUnit, servingSize, restaurantID, fromFatsecret, countUsed) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
				pstmt.setInt(10, 1);

				pstmt.executeUpdate();

				ResultSet rs = pstmt.getGeneratedKeys();

				if (rs.next())
					entryID = rs.getInt(1);

				return entryID;

			} catch (Exception e) {
				throw new DataAccessException(
						"An error has occured while trying to access data from the database",
						e);
			}
		}
	}

	@Override
	public int foodEntryExists(Food food) throws DataAccessException {

		try {
			Connection conn = getConnection();
			String query = "SELECT * FROM foodlist WHERE "
					+ "name = ?, calorie = ?, protein = ?, fat = ?, carbohydrate = ?, servingUnit = ?, servingSize = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, food.getName());
			pstmt.setDouble(2, food.getCalorie());
			pstmt.setDouble(3, food.getFat());
			pstmt.setDouble(4, food.getCarbohydrate());
			pstmt.setString(5, food.getServingUnit());
			pstmt.setDouble(6, food.getServingSize());

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
	public List<Food> getAllFood() throws DataAccessException {

		List<Food> foods = new ArrayList<Food>();

		try {
			Connection conn = getConnection();
			String query = "SELECT id, name, calorie, servingUnit, servingSize, restaurantID, fromFatsecret FROM foodList";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				foods.add(new Food(rs.getInt("id"), rs.getString("name"), rs
						.getDouble("calorie"), rs.getDouble("protein"), rs
						.getDouble("fat"), rs.getDouble("carbohydrate"), rs
						.getString("servingUnit"), rs.getDouble("servingSize"),
						rs.getInt("restaurantID"), rs
								.getBoolean("fromFatsecret")));
			}
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

		return foods;
	}

	@Override
	public Food getFood(int entryID) throws DataAccessException {
		Food food = new Food(entryID);

		try {
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
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

		return food;

	}

	@Override
	public List<Food> search(String searchQuery) throws DataAccessException {
		List<Food> foods = new ArrayList<Food>();

		try {
			Connection conn = getConnection();
			String query = "SELECT * FROM foodList WHERE name LIKE ? ORDER BY countUsed DESC";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, searchQuery);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				foods.add(new Food(rs.getInt("id"), rs.getString("name"), rs
						.getDouble("calorie"), rs.getDouble("protein"), rs
						.getDouble("fat"), rs.getDouble("carbohydrate"), rs
						.getString("servingUnit"), rs.getDouble("servingSize"),
						rs.getInt("restaurantID"), rs
								.getBoolean("fromFatsecret")));
			}
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

		return foods;
	}

	public void incrementCountUsed(Food food) throws DataAccessException {
		
		try{
			Connection conn = getConnection();
			String query = "UPDATE foodlist SET countUsed = countUsed + 1 WHERE id = ? ";
			
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, food.getEntryID() );
			
			pstmt.executeUpdate();
			
		}catch (Exception e){
			throw new DataAccessException(
					"An error has occured while trying to access data from the database", e);
		}
	}

}
