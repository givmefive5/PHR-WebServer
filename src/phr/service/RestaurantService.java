package phr.service;

import java.util.List;

import phr.exceptions.ServiceException;
import phr.models.Food;

public interface RestaurantService {
	
    public List<Food> getFood (int restaurantID) throws ServiceException;
	
	public Integer getRestaurantID (String restaurantName) throws ServiceException;

}
