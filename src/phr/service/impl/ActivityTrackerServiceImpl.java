package phr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.dao.ActivityTrackerDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.exceptions.ServiceException;
import phr.models.ActivityTrackerEntry;
import phr.models.User;
import phr.service.ActivityTrackerService;

@Service("activityTrackerService")
public class ActivityTrackerServiceImpl implements ActivityTrackerService {
	
	@Autowired
	ActivityTrackerDao activityTrackerDao;
	
	@Autowired 
	UserDao userDao;
	

	@Override
	public int addReturnEntryID(String accessToken, ActivityTrackerEntry activityTrackerEntry)
			throws ServiceException {
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			activityTrackerEntry.setUser(new User(userID));
			return activityTrackerDao.addReturnsEntryID(activityTrackerEntry); 
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while adding a activity entry in the tracker", e);
		}
	}

	@Override
	public void edit(String accessToken, ActivityTrackerEntry activityTrackerEntry)
			throws ServiceException, EntryNotFoundException {
		
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			activityTrackerEntry.setUser(new User(userID));
			activityTrackerDao.edit(activityTrackerEntry);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while editing a activity entry", e);
		}
		
	}

	@Override
	public void delete(String accessToken, ActivityTrackerEntry activityTrackerEntry)
			throws ServiceException, EntryNotFoundException {
		
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			activityTrackerEntry.setUser(new User(userID));
			activityTrackerDao.delete(activityTrackerEntry);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while deleting a activity entry", e);
		}

		
	}

	@Override
	public List<ActivityTrackerEntry> getAll(String accessToken)
			throws ServiceException {
		
		List<ActivityTrackerEntry> activities = new ArrayList<ActivityTrackerEntry>();
		
		try{
			activities = activityTrackerDao.getAll(accessToken);
		}catch(DataAccessException e){
			e.printStackTrace();
			throw new ServiceException(
					"Error has occured while getting all entries in activity tracker", e);
		}
		
		return activities;
	}

	@Override
	public Integer getEntryId(ActivityTrackerEntry activityTrackerEntry)
			throws ServiceException {
		if (activityTrackerEntry.getEntryID() != null)
			return activityTrackerEntry.getEntryID();
		else
			try {
				return activityTrackerDao.getEntryId(activityTrackerEntry);
			} catch (DataAccessException e) {
				throw new ServiceException(
						"Error has occurred while getting the entry id a activity entry",
						e);
			}
	}
}
