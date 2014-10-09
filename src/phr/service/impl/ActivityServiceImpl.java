package phr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.dao.ActivityDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.ServiceException;
import phr.service.ActivityService;
import phr.web.models.Activity;

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {
	
	@Autowired
	ActivityDao activityDao;
	
	@Override
	public List<Activity> search(String searchQuery) throws ServiceException {
		
		List<Activity> activities = new ArrayList<Activity>();
		
		try {
			activities = activityDao.search(searchQuery);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"Error has occured while searching for activities entries", e);
		}
		return activities;
	}

	@Override
	public int addActivityListEntryReturnEntryID(Activity activity)
			throws ServiceException {
		try {
			return activityDao.addActivityListEntryReturnEntryID(activity);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while adding a activity entry in the list", e);
		}
	}

}