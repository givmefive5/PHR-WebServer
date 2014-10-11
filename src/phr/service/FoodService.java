package phr.service;

import java.util.List;

import phr.exceptions.ServiceException;
import phr.web.models.Food;

public interface FoodService {
	
	public List<Food> search(String searchQuery) throws ServiceException;
	
	public int addReturnEntryID(Food food) throws ServiceException;


}
