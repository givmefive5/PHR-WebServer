package phr.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.dao.ActivityDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.ServiceException;
import phr.service.ActivityService;
import phr.web.models.Activity;
import phr.web.models.ActivityTrackerEntry;
import phr.web.models.User;

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {
	
	@Autowired
	ActivityDao activityDao;
	
	@Autowired 
	UserDao userDao;
	

	@Override
	public void add(String accessToken, ActivityTrackerEntry activityTrackerEntry)
			throws ServiceException {
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			activityTrackerEntry.setUser(new User(userID));
			
			if(activityTrackerEntry.getActivity().getEntryID() == null)
				activityDao.addActivityListEntry(activityTrackerEntry.getActivity());
			
			activityDao.add(activityTrackerEntry);
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while adding a activity entry in the tracker", e);
		}
	}

	@Override
	public void edit(String accessToken, ActivityTrackerEntry object)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String accessToken, ActivityTrackerEntry object)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<ActivityTrackerEntry> getAll(String accessToken)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
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
						"Error has occurred while adding a activity entry",
						e);
			}
	}
}
