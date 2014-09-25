package phr.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.dao.ActivityDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.exceptions.ServiceException;
import phr.service.ActivityService;
import phr.web.models.ActivityTrackerEntry;
import phr.web.models.User;

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {
	
	@Autowired
	ActivityDao activityDao;
	
	@Autowired 
	UserDao userDao;
	

	@Override
	public int addReturnEntryID(String accessToken, ActivityTrackerEntry activityTrackerEntry)
			throws ServiceException {
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			activityTrackerEntry.setUser(new User(userID));
			
			if(activityTrackerEntry.getActivity().getEntryID() == null)
				activityTrackerEntry.getActivity().setEntryID(activityDao.addActivityListEntryReturnEntryID(activityTrackerEntry.getActivity()));
			
			return activityDao.addReturnsEntryID(activityTrackerEntry); 
			
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
			activityDao.edit(activityTrackerEntry);
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
			activityDao.delete(activityTrackerEntry);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while deleting a activity entry", e);
		}

		
	}

	@Override
	public ArrayList<ActivityTrackerEntry> getAll(String accessToken)
			throws ServiceException {
		
		ArrayList<ActivityTrackerEntry> activities = new ArrayList<ActivityTrackerEntry>();
		
		try{
			activities = activityDao.getAll(accessToken);
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
				return activityDao.getEntryId(activityTrackerEntry);
			} catch (DataAccessException e) {
				throw new ServiceException(
						"Error has occurred while getting the entry id a activity entry",
						e);
			}
	}
}
