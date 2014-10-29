package phr.dao;

import java.util.List;

import phr.exceptions.DataAccessException;
import phr.models.Food;

public interface FoodDao {
	
	public int addReturnEntryID(Food food) throws DataAccessException;
	
	public int foodEntryExists(Food food) throws DataAccessException;
	
	public List<Food> getAllFood () throws DataAccessException;
	
	public Food getFood(int entryID) throws DataAccessException;

	public List<Food> search(String searchQuery) throws DataAccessException;
	
	public List<Food> getFoodGivenRestaurantName(String restaurantName) throws DataAccessException;
    
	public Integer getRestaurantID(String restaurantName) throws DataAccessException;
	
	public List<Food> suggest (String searchQuery) throws DataAccessException;

}
