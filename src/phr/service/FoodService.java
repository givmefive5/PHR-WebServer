package phr.service;

import java.util.List;

import phr.exceptions.ServiceException;
import phr.web.models.Food;

public interface FoodService {
	
	List<Food> search(String searchQuery) throws ServiceException;
	
	int addFoodListEntryReturnEntryID(Food food) throws ServiceException;


}
