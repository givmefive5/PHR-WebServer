package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.BloodPressureDao;
import com.example.dao.UserDao;
import com.example.exceptions.DataAccessException;
import com.example.exceptions.TrackerServiceException;
import com.example.model.BloodPressure;
import com.example.service.BloodPressureService;

@Service("bloodPressureService")
public class BloodPressureServiceImpl implements BloodPressureService {

	@Autowired
	BloodPressureDao bloodPressureDao;

	@Autowired
	UserDao userDao;

	@Override
	public void addBloodPressure(String username, BloodPressure bloodPressure)
			throws TrackerServiceException {
		try {
			int userID = userDao.getUserIdGivenUsername(username);
			bloodPressure.setUserID(userID);
			bloodPressureDao.addBloodPressure(bloodPressure);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new TrackerServiceException(
					"Error has occurred while adding a blood pressure entry", e);
		}
	}

	@Override
	public Integer getEntryID(BloodPressure bloodPressure)
			throws TrackerServiceException {
		if (bloodPressure.getEntryID() != null)
			return bloodPressure.getEntryID();
		else
			try {
				return bloodPressureDao.getIdFromDatabase(bloodPressure);
			} catch (DataAccessException e) {
				throw new TrackerServiceException(
						"Error has occurred while adding a blood pressure entry",
						e);
			}
	}
}
