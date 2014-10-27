package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Repository;

import phr.dao.RestaurantDao;
import phr.exceptions.DataAccessException;
import phr.models.Restaurant;

@Repository("restaurantDao")
public class RestaurantDaoSqlImpl extends BaseDaoSqlImpl implements
		RestaurantDao {

	@Override
	public Restaurant getRestaurantGivenRestaurantName(String restaurantName) throws DataAccessException {
		Restaurant restaurant = null;
		
		try{
			Connection conn = getConnection();
			String query = "SELECT * FROM restaurantlist WHERE name = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, restaurantName);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				restaurant = new Restaurant(rs.getInt("id"), rs.getString("name"));
			}
			
		}catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

		return restaurant;
	}

	@Override
	public Restaurant getRestaurantGivenRestaurantID(int restaurantID) throws DataAccessException {
		
		Restaurant restaurant = new Restaurant(restaurantID);

		try {
			Connection conn = getConnection();
			String query = "SELECT * FROM restaurantlist WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, restaurantID);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				restaurant.setName("name");
			}
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

		return restaurant;
	}

}
