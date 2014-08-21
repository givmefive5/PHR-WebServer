package com.example.dao;

import com.example.exceptions.DataAccessException;
import com.example.model.BloodPressure;

public interface BloodPressureDao {

	void addBloodPressure(String username, BloodPressure bloodPressure)
			throws DataAccessException;

}
