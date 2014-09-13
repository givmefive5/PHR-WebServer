package phr.dao;

import phr.exceptions.DataAccessException;
import phr.web.models.BloodSugar;

public interface BloodSugarDao {

	public void addBloodSugar(BloodSugar bloodSugar) throws DataAccessException;

	public Integer getIdFromDatabase(BloodSugar bloodSugar)
			throws DataAccessException;

}
