package phr.service;

import java.util.List;

import phr.exceptions.ServiceException;
import phr.web.models.Activity;

public interface ActivityService {
	
	List<Activity> search(String query) throws ServiceException;
	
	int addActivityListEntryReturnEntryID(Activity activity) throws ServiceException;
}
