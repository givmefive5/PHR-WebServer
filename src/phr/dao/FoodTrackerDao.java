package phr.dao;

import java.util.List;

import phr.exceptions.DataAccessException;
import phr.web.models.Food;
import phr.web.models.FoodTrackerEntry;

public interface FoodTrackerDao extends TrackerDao<FoodTrackerEntry> {
	
	public int addFoodListEntryReturnEntryID(Food food) throws DataAccessException;
	
	public int foodEntryExists(Food food) throws DataAccessException;
	
	public List<Food> getAllFood () throws DataAccessException;
	
	public Food getFood(int entryID) throws DataAccessException;

	public List<Food> search(String searchQuery) throws DataAccessException;

}
