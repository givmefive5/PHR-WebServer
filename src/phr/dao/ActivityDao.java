package phr.dao;

import java.util.List;

import phr.exceptions.DataAccessException;
import phr.web.models.Activity;

public interface ActivityDao {
	
	public int addActivityListEntryReturnEntryID(Activity activity) throws DataAccessException;
	
	public List<Activity> getAllActivity() throws DataAccessException;
	
	public Activity getActivity(int entryID) throws DataAccessException;
	
	public List<Activity> search(String serachQuery) throws DataAccessException;

}
