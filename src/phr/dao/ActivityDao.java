package phr.dao;

import java.util.List;

import phr.exceptions.DataAccessException;
import phr.models.Activity;

public interface ActivityDao {
	
	public int addReturnEntryID(Activity activity) throws DataAccessException;
	
	public List<Activity> getAllActivity() throws DataAccessException;
	
	public Activity getActivity(int entryId) throws DataAccessException;
	
	public Double getActivityMET(String activityName) throws DataAccessException;
	
	public int ActivityEntryExistsReturnEntryID(Activity activity) throws DataAccessException;
	
	public List<Activity> search(String serachQuery) throws DataAccessException;

}
