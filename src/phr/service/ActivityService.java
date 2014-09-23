package phr.service;

import phr.exceptions.ServiceException;
import phr.web.models.Activity;
import phr.web.models.ActivityTrackerEntry;

public interface ActivityService extends TrackerService<ActivityTrackerEntry> {
	
	public void addActivityListEntry(Activity activity) throws ServiceException;

}
