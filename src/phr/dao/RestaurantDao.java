package phr.dao;

import phr.exceptions.DataAccessException;
import phr.models.Restaurant;

public interface RestaurantDao {
	
	public Restaurant getRestaurantGivenRestaurantName(String restaurantName) throws DataAccessException;
	
	public Restaurant getRestaurantGivenRestaurantID(int restaurantID) throws DataAccessException;

}
