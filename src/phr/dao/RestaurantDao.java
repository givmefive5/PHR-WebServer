package phr.dao;

import java.util.List;

import phr.exceptions.DataAccessException;
import phr.web.models.Food;

public interface RestaurantDao {
	
	public List<Food> getFood (int restaurantID) throws DataAccessException;
	
	public Integer getRestaurantID (String restaurantName) throws DataAccessException;

}
