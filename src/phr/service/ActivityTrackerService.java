package phr.service;

import java.util.List;

import phr.exceptions.ServiceException;
import phr.web.models.Activity;
import phr.web.models.ActivityTrackerEntry;

public interface ActivityTrackerService extends TrackerService<ActivityTrackerEntry> {
	
	List<Activity> search(String query) throws ServiceException;
	
	int addActivityListEntryReturnEntryID(Activity activity) throws ServiceException;
}
