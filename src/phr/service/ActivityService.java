package phr.service;

import java.util.List;

import phr.exceptions.EntryNotFoundException;
import phr.exceptions.ServiceException;
import phr.models.Activity;

public interface ActivityService {

	public List<Activity> search(String query) throws ServiceException;

	public int addReturnEntryID(Activity activity) throws ServiceException;

	public List<Activity> getAll() throws ServiceException;

	public List<Activity> getActivityGivenGymName(String gymName)
			throws ServiceException;

	public void delete(Activity activity) throws ServiceException,
			EntryNotFoundException;

}
