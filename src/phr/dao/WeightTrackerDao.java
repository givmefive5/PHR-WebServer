package phr.dao;

import phr.exceptions.DataAccessException;
import phr.models.Weight;

public interface WeightTrackerDao extends TrackerDao<Weight> {
	
	public Weight getLatestWeight(String userAccessToken) throws DataAccessException;

}
