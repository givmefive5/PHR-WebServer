package phr.dao;

import java.util.List;

import phr.exceptions.DataAccessException;
import phr.models.Activity;

public interface SportEstablishmentDao {

	public List<Activity> getActivity (int gymID) throws DataAccessException;
	
	public Integer getGymID (String gymName) throws DataAccessException;
}
