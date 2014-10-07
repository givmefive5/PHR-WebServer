package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import phr.dao.RestaurantDao;
import phr.exceptions.DataAccessException;
import phr.web.models.Food;

public class RestaurantDaoSqlImpl extends BaseDaoSqlImpl implements RestaurantDao {

	
	// sort by the count column
	@Override
	public List<Food> getFood(int restaurantID) throws DataAccessException {
		List<Food> foods = new ArrayList<Food>();
		
		try{
			Connection conn = getConnection();
			String query = "SELECT * FROM foodList WHERE restaurantID = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, restaurantID);
			
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
		}catch(Exception e){
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",e);
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
