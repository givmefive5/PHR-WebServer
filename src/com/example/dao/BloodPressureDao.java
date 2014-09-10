package com.example.dao;

import com.example.exceptions.DataAccessException;
import com.example.model.BloodPressure;

public interface BloodPressureDao {

	public void addBloodPressure(BloodPressure bloodPressure)
			throws DataAccessException;

	public Integer getIdFromDatabase(BloodPressure bloodPressure)
			throws DataAccessException;

}
