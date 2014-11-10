package phr.service;

import java.util.List;

import phr.exceptions.EntryNotFoundException;
import phr.exceptions.ServiceException;
import phr.models.Food;

public interface FoodService {

	public List<Food> search(String searchQuery) throws ServiceException;

	public int addReturnEntryID(Food food) throws ServiceException;

	public List<Food> getAll() throws ServiceException;

	public List<Food> getFoodGivenRestaurantName(String restaurantName)
			throws ServiceException;

	public void delete(Food food) throws ServiceException,
			EntryNotFoundException;

}
