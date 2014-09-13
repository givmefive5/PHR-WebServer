package phr.dao;

import phr.exceptions.DataAccessException;
import phr.web.models.BloodPressure;

public interface BloodPressureDao {

	public void addBloodPressure(BloodPressure bloodPressure)
			throws DataAccessException;

	public Integer getIdFromDatabase(BloodPressure bloodPressure)
			throws DataAccessException;

}
