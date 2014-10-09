package phr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.dao.FoodTrackerDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.exceptions.ServiceException;
import phr.service.FoodTrackerService;
import phr.web.models.FoodTrackerEntry;
import phr.web.models.User;

@Service("foodTrackerService")

public class FoodTrackerServiceImpl implements FoodTrackerService {
	
	@Autowired 
	FoodTrackerDao foodTrackerDao;
	
	@Autowired
	UserDao userDao;

	@Override
	public int addReturnEntryID(String accessToken, FoodTrackerEntry foodTrackerEntry)
			throws ServiceException {
		
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			foodTrackerEntry.setUser(new User(userID));	
			return foodTrackerDao.addReturnsEntryID(foodTrackerEntry);
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while adding a food entry in the tracker", e);
		}
		
	}

	@Override
	public void edit(String accessToken, FoodTrackerEntry foodTrackerEntry)
			throws ServiceException, EntryNotFoundException {
		
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			foodTrackerEntry.setUser(new User(userID));
			foodTrackerDao.edit(foodTrackerEntry);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while editing a food entry", e);
		}
		
	}

	@Override
	public void delete(String accessToken, FoodTrackerEntry foodTrackerEntry)
			throws ServiceException, EntryNotFoundException {
		
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			foodTrackerEntry.setUser(new User(userID));
			foodTrackerDao.delete(foodTrackerEntry);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while deleting a food entry", e);
		}
	}

	@Override
	public List<FoodTrackerEntry> getAll(String accessToken)
			throws ServiceException {
		
		List<FoodTrackerEntry> foods = new ArrayList<FoodTrackerEntry>();
		
		try{
			foods = foodTrackerDao.getAll(accessToken);
		}catch(DataAccessException e){
			e.printStackTrace();
			throw new ServiceException(
					"Error has occured while getting all entries in food tracker", e);
		}
		
		return foods;
	}

	@Override
	public Integer getEntryId(FoodTrackerEntry foodTrackerEntry) throws ServiceException {
		if (foodTrackerEntry.getEntryID() != null)
			return foodTrackerEntry.getEntryID();
		else
			try {
				return foodTrackerDao.getEntryId(foodTrackerEntry);
			} catch (DataAccessException e) {
				throw new ServiceException(
						"Error has occurred while getting the entry id of a food entry",
						e);
			}
	}
}
