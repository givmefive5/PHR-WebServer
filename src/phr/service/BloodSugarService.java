package phr.service;

import phr.exceptions.DataAccessException;
import phr.exceptions.TrackerServiceException;
import phr.web.models.BloodSugar;

public interface BloodSugarService {

	public void addBloodSugar(String username, BloodSugar bloodSugar)
			throws DataAccessException, TrackerServiceException;

	public Integer getIdFromDatabase(BloodSugar bloodSugar)
			throws DataAccessException, TrackerServiceException;

}
