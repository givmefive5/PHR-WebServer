package phr.dao;

import java.util.List;

import phr.exceptions.DataAccessException;
import phr.models.Activity;

public interface ActivityDao {

	public int addReturnEntryID(Activity activity) throws DataAccessException;

	public List<Activity> getAllActivity() throws DataAccessException;

	public Activity getActivity(int entryId) throws DataAccessException;

	public Double getActivityMET(String activityName)
			throws DataAccessException;

	public int ActivityEntryExistsReturnEntryID(Activity activity)
			throws DataAccessException;

	public List<Activity> search(String serachQuery) throws DataAccessException;

	public List<Activity> getActivityListGivenGymName(String gymName)
			throws DataAccessException;

	public Integer getActivityID(String activityName)
			throws DataAccessException;

	public Integer getGymID(String gymName) throws DataAccessException;

	public Activity getActivityGivenName(String query)
			throws DataAccessException;

	public void delete(Activity activity);
}
