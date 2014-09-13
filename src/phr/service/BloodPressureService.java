package phr.service;

import phr.exceptions.TrackerServiceException;
import phr.web.models.BloodPressure;

public interface BloodPressureService {

	public void addBloodPressure(String username, BloodPressure bloodPressure)
			throws TrackerServiceException;

	public Integer getEntryID(BloodPressure bloodPressure)
			throws TrackerServiceException;
}
