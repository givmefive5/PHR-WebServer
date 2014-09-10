package com.example.service;

import com.example.exceptions.TrackerServiceException;
import com.example.model.BloodPressure;

public interface BloodPressureService {

	public void addBloodPressure(String username, BloodPressure bloodPressure)
			throws TrackerServiceException;

	public Integer getEntryID(BloodPressure bloodPressure)
			throws TrackerServiceException;
}
