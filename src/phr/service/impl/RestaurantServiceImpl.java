package phr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.dao.RestaurantDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.ServiceException;
import phr.models.Food;
import phr.service.RestaurantService;

@Service("restaurantService")

public class RestaurantServiceImpl implements RestaurantService {

	@Autowired 
	RestaurantDao restaurantDao;
	
	@Override
	public List<Food> getFood(int restaurantID) throws ServiceException {
		List<Food> foods = new ArrayList<Food>();
		try {
			foods = restaurantDao.getFood(restaurantID);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while getting food entries", e);
		}
		return foods;
	}

	@Override
	public Integer getRestaurantID(String restaurantName)
			throws ServiceException {
		
		try {
			return restaurantDao.getRestaurantID(restaurantName);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while getting restaurantID", e);
		}
	}

}
