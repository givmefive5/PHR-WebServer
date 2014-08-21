package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.BloodPressureDao;
import com.example.exceptions.BloodPressureServiceException;
import com.example.exceptions.DataAccessException;
import com.example.model.BloodPressure;
import com.example.service.BloodPressureService;

@Service("bloodPressureService")
public class BloodPressureServiceImpl implements BloodPressureService {

	@Autowired
	BloodPressureDao bloodPressureDao;

	@Override
	public void addBloodPressure(String username, BloodPressure bloodPressure)
			throws BloodPressureServiceException {
		try {
			bloodPressureDao.addBloodPressure(username, bloodPressure);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new BloodPressureServiceException(
					"Error has occurred while adding a blood pressure entry", e);
		}
	}

}
