package phr.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.dao.FoodDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.ServiceException;
import phr.service.FoodService;
import phr.web.models.FoodTrackerEntry;
import phr.web.models.User;

@Service("foodService")

public class FoodServiceImpl implements FoodService {
	
	@Autowired 
	FoodDao foodDao;
	
	@Autowired
	UserDao userDao;

	@Override
	public void add(String accessToken, FoodTrackerEntry foodTrackerEntry)
			throws ServiceException {
		
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			foodTrackerEntry.setUser(new User(userID));
			
			if(foodTrackerEntry.getFood().getEntryID() == null){
				if(!foodDao.checkFoodEntryInList(foodTrackerEntry.getFood()))
					foodDao.addFoodListEntry(foodTrackerEntry.getFood());
			}
				
			foodDao.add(foodTrackerEntry);
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while adding a food entry in the tracker", e);
		}
		
	}

	@Override
	public void edit(String accessToken, FoodTrackerEntry object)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String accessToken, FoodTrackerEntry object)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<FoodTrackerEntry> getAll(String accessToken)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getEntryId(FoodTrackerEntry foodTrackerEntry) throws ServiceException {
		if (foodTrackerEntry.getEntryID() != null)
			return foodTrackerEntry.getEntryID();
		else
			try {
				return foodDao.getEntryId(foodTrackerEntry);
			} catch (DataAccessException e) {
				throw new ServiceException(
						"Error has occurred while adding a food entry",
						e);
			}
	}

}
