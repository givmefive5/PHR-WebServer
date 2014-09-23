package phr.dao;

import phr.exceptions.DataAccessException;
import phr.web.models.Food;
import phr.web.models.FoodTrackerEntry;

public interface FoodDao extends TrackerDao<FoodTrackerEntry> {
	
	public void addFoodListEntry(Food food) throws DataAccessException;
	
	public Boolean checkFoodEntryInList(Food food) throws DataAccessException;

}
