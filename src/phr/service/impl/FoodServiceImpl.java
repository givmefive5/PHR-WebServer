package phr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import phr.dao.FoodDao;
import phr.dao.sqlimpl.FoodDaoSqlImpl;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.exceptions.FatSecretFetcherException;
import phr.exceptions.ServiceException;
import phr.fatsecret.FatSecretFetcher;
import phr.models.Food;
import phr.service.FoodService;

@Service("foodService")
public class FoodServiceImpl implements FoodService {

	// @Autowired
	// FoodDao foodDao;

	FoodDao foodDao = new FoodDaoSqlImpl();

	@Override
	public List<Food> search(String searchQuery) throws ServiceException {

		List<Food> foods = new ArrayList<Food>();
		try {
			foods.addAll(foodDao.suggest(searchQuery));
			foods.addAll(FatSecretFetcher.searchFood(searchQuery));
			foods.addAll(foodDao.search(searchQuery));
			if ((searchQuery == "" || searchQuery == null) && foods.size() > 30)
				return foods.subList(0, 30);
			else if (searchQuery == "" || searchQuery == null)
				return foods.subList(0, foods.size());
			else
				return foods;
		} catch (DataAccessException | FatSecretFetcherException e) {
			throw new ServiceException(
					"Error has occured while searching for food entries", e);
		}

	}

	@Override
	public int addReturnEntryID(Food food) throws ServiceException {

		try {
			return foodDao.addReturnEntryID(food);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while adding a food entry in the list",
					e);
		}
	}

	@Override
	public List<Food> getAll() throws ServiceException {
		try {
			return foodDao.getAllFood();
		} catch (DataAccessException e) {
			throw new ServiceException("An error has occurred", e);
		}
	}

	@Override
	public List<Food> getFoodGivenRestaurantName(String restaurantName)
			throws ServiceException {
		try {
			return foodDao.getFoodListGivenRestaurantName(restaurantName);
		} catch (DataAccessException e) {
			throw new ServiceException("An error has occurred", e);
		}
	}

	@Override
	public void delete(Food food) throws ServiceException,
			EntryNotFoundException {
		foodDao.delete(food);
	}

}
