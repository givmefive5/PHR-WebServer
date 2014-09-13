package phr.service;

import phr.exceptions.TrackerServiceException;
import phr.web.models.BloodPressure;

public interface BloodPressureService extends TrackerService<BloodPressure> {

	public Integer getEntryID(BloodPressure bloodPressure)
			throws TrackerServiceException;

}
