package phr.dao;

import phr.exceptions.DataAccessException;
import phr.web.models.Activity;
import phr.web.models.ActivityTrackerEntry;

public interface ActivityDao extends TrackerDao<ActivityTrackerEntry> {
	
	public void addActivityListEntry(Activity activity) throws DataAccessException;

}
