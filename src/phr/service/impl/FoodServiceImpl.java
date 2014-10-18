package phr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.dao.FoodDao;
import phr.dao.sqlimpl.FoodDaoSqlImpl;
import phr.exceptions.DataAccessException;
import phr.exceptions.ServiceException;
import phr.models.Food;
import phr.service.FoodService;

@Service("foodService")
public class FoodServiceImpl implements FoodService {
	
	//@Autowired
	//FoodDao foodDao;
	
	FoodDao foodDao = new FoodDaoSqlImpl();
	
	@Override
	public List<Food> search(String searchQuery)
			throws ServiceException {
		
		List<Food> foods = new ArrayList<Food>();
		try {
			foods = foodDao.search(searchQuery);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"Error has occured while searching for food entries", e);
		}
		return foods;
	}

	@Override
	public int addReturnEntryID(Food food) throws ServiceException {
		
		try {
			return foodDao.addReturnEntryID(food);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while adding a food entry in the list", e);
		}
	}

	@Override
	public List<Food> getAll() throws ServiceException{
		try {
			return foodDao.getAllFood();
		} catch (DataAccessException e) {
			throw new ServiceException("An error has occurred", e);
		}
	}

}
