package phr.service;

import java.util.List;

import phr.exceptions.ServiceException;
import phr.web.models.Food;
import phr.web.models.FoodTrackerEntry;

public interface FoodTrackerService extends TrackerService<FoodTrackerEntry> {

	List<Food> search(String searchQuery) throws ServiceException;
	
	int addFoodListEntryReturnEntryID(Food food) throws ServiceException;

}
