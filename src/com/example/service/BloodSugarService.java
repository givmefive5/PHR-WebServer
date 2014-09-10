package com.example.service;

import com.example.exceptions.DataAccessException;
import com.example.exceptions.TrackerServiceException;
import com.example.model.BloodSugar;

public interface BloodSugarService {

	public void addBloodSugar(String username, BloodSugar bloodSugar)
			throws DataAccessException, TrackerServiceException;

	public Integer getIdFromDatabase(BloodSugar bloodSugar)
			throws DataAccessException, TrackerServiceException;

}
