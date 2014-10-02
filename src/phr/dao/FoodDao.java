package phr.dao;

import java.util.ArrayList;

import phr.exceptions.DataAccessException;
import phr.web.models.Food;
import phr.web.models.FoodTrackerEntry;

public interface FoodDao extends TrackerDao<FoodTrackerEntry> {
	
	public int addFoodListEntryReturnEntryID(Food food) throws DataAccessException;
	
	public Boolean foodEntryExists(Food food) throws DataAccessException;
	
	public ArrayList<Food> getAllFood () throws DataAccessException;

}

