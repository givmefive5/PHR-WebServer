package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
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

		if (food.getEntryID() != null) {
			incrementCountUsed(food);
			return food.getEntryID();
		}
		int entryID = foodEntryExists(food);
		if (entryID != -1) {

			incrementCountUsed(food);

			return entryID;
		} else {

			try {

				Connection conn = getConnection();
				String query = "INSERT INTO foodlist(name, calorie, protein, fat, carbohydrate, serving, restaurantID, fromFatsecret, countUsed) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement pstmt;

				pstmt = conn.prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, food.getName());
				pstmt.setDouble(2, food.getCalorie());
				pstmt.setDouble(3, food.getProtein());
				pstmt.setDouble(4, food.getFat());
				pstmt.setDouble(5, food.getCarbohydrate());
				pstmt.setString(6, food.getServing());

				if (food.getRestaurantID() == null)
					pstmt.setNull(7, Types.NULL);
				else
					pstmt.setInt(7, food.getRestaurantID());
				pstmt.setBoolean(8, food.getFromFatsecret());
				pstmt.setInt(9, 0);

				pstmt.executeUpdate();

				ResultSet rs = pstmt.getGeneratedKeys();

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
	}

	@Override
	public int foodEntryExists(Food food) throws DataAccessException {

		try {
			Connection conn = getConnection();
			String query = "SELECT * FROM foodlist WHERE "
					+ "name = ?, calorie = ?, protein = ?, fat = ?, carbohydrate = ?, serving = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, food.getName());
			pstmt.setDouble(2, food.getCalorie());
			pstmt.setDouble(3, food.getFat());
			pstmt.setDouble(4, food.getCarbohydrate());
			pstmt.setString(5, food.getServing());

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
	public List<Food> getAllFood() throws DataAccessException {

		List<Food> foods = new ArrayList<Food>();

		try {
			Connection conn = getConnection();
			String query = "SELECT id, name, calorie, serving, restaurantID, fromFatsecret, countUsed FROM foodList";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				foods.add(new Food(rs.getInt("id"), rs.getString("name"), rs
						.getDouble("calorie"), rs.getDouble("protein"), rs
						.getDouble("fat"), rs.getDouble("carbohydrate"), rs
						.getString("serving"), rs.getInt("restaurantID"), rs
						.getBoolean("fromFatsecret"), rs.getInt("countUsed")));
			}
			conn.close();
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
			String query = "SELECT *"
					+ " FROM foodList WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, entryID);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				food.setName(rs.getString("name"));
				food.setCalorie(rs.getDouble("calorie"));
				food.setServing(rs.getString("serving"));
				food.setRestaurantID(rs.getInt("restaurantID"));
				food.setFromFatsecret(rs.getBoolean("fromFatsecret"));
				food.setProtein(rs.getDouble("protein"));
				food.setFat(rs.getDouble("fat"));
				food.setCarbohydrate(rs.getDouble("carbohydrate"));
			}
			conn.close();
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
			pstmt.setString(1, "%" + searchQuery + "%");

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				foods.add(new Food(rs.getInt("id"), rs.getString("name"), rs
						.getDouble("calorie"), rs.getDouble("protein"), rs
						.getDouble("fat"), rs.getDouble("carbohydrate"), rs
						.getString("serving"), rs.getInt("restaurantID"), rs
						.getBoolean("fromFatsecret"), rs.getInt("countUsed")));
			}
			conn.close();
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

		return foods;
	}

	public void incrementCountUsed(Food food) throws DataAccessException {

		try {
			Connection conn = getConnection();
			String query = "UPDATE foodlist SET countUsed = countUsed + 1 WHERE id = ? ";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			System.out.println(food.getEntryID());
			pstmt.setInt(1, food.getEntryID());

			pstmt.executeUpdate();
			conn.close();
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
	}

	@Override
	public List<Food> getFoodListGivenRestaurantName(String restaurantName)
			throws DataAccessException {
		List<Food> foods = new ArrayList<Food>();

		try {
			Connection conn = getConnection();
			String query = "SELECT * FROM foodList WHERE restaurantID = ? "
					+ "ORDER BY countUsed DESC";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, getRestaurantID(restaurantName));

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				foods.add(new Food(rs.getInt("id"), rs.getString("name"), rs
						.getDouble("calorie"), rs.getDouble("protein"), rs
						.getDouble("fat"), rs.getDouble("carbohydrate"), rs
						.getString("serving"), rs.getInt("restaurantID"), rs
						.getBoolean("fromFatsecret"), rs.getInt("countUsed")));
			}
			conn.close();
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
		return foods;
	}

	@Override
	public Integer getRestaurantID(String restaurantName)
			throws DataAccessException {

		try {
			Connection conn = getConnection();
			String query = "SELECT id FROM restaurantlist WHERE name = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, restaurantName);

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

	@Override
	public List<Food> suggest(String searchQuery) throws DataAccessException {

		List<Food> foods = new ArrayList<Food>();

		try {
			Connection conn = getConnection();

			String query = "SELECT * FROM foodlist WHERE calorie != null AND "
					+ "protein != null AND fat != null AND carbohydrate != null AND name LIKE ?"
					+ "ORDER BY countUsed DESC";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + searchQuery + "%");

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				foods.add(new Food(rs.getInt("id"), rs.getString("name"), rs
						.getDouble("calorie"), rs.getDouble("protein"), rs
						.getDouble("fat"), rs.getDouble("carbohydrate"), rs
						.getString("serving"), rs.getInt("restaurantID"), rs
						.getBoolean("fromFatsecret"), rs.getInt("countUsed")));
			}
			conn.close();
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
		return foods;
	}

	@Override
	public Food getFoodGivenName(String searchQuery) throws DataAccessException {

		Food food = null;
		try {
			Connection conn = getConnection();

			String query = "SELECT * FROM foodlist WHERE name = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, searchQuery);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				food = new Food(rs.getInt("id"));
				food.setName(rs.getString("name"));
				food.setCalorie(rs.getDouble("calorie"));
				food.setServing(rs.getString("serving"));
				food.setRestaurantID(rs.getInt("restaurantID"));
				food.setFromFatsecret(rs.getBoolean("fromFatsecret"));
				food.setProtein(rs.getDouble("protein"));
				food.setFat(rs.getDouble("fat"));
				food.setCarbohydrate(rs.getDouble("carbohydrate"));
				food.setCountUsed(rs.getInt("countUsed"));
			}
			conn.close();
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while tyring to access data from the database",
					e);
		}
		return food;
	}

	@Override
	public void delete(Food food) {
		// TODO Auto-generated method stub

	}
}
