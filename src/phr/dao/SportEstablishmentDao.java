package phr.dao;

import phr.exceptions.DataAccessException;
import phr.models.SportEstablishment;

public interface SportEstablishmentDao {
	
	public SportEstablishment getSportEstablishmentGivenGymName(String gymName) throws DataAccessException;
	
	public SportEstablishment getSportEstablishmentGivenGymID(int gymID) throws DataAccessException;
}
