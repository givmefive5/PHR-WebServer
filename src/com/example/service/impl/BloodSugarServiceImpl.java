package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.dao.BloodSugarDao;
import com.example.dao.UserDao;
import com.example.exceptions.DataAccessException;
import com.example.exceptions.TrackerServiceException;
import com.example.model.BloodSugar;
import com.example.service.BloodSugarService;

public class BloodSugarServiceImpl implements BloodSugarService {

	@Autowired
	BloodSugarDao bloodSugarDao;

	@Autowired
	UserDao userDao;

	@Override
	public void addBloodSugar(String username, BloodSugar bloodSugar)
			throws TrackerServiceException {
		try {
			int userID = userDao.getUserIdGivenUsername(username);
			bloodSugar.setUserID(userID);
			bloodSugarDao.addBloodSugar(bloodSugar);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new TrackerServiceException(
					"Error has occurred while adding a blood pressure entry", e);
		}
	}

	@Override
	public Integer getIdFromDatabase(BloodSugar bloodSugar)
			throws TrackerServiceException {
		if (bloodSugar.getUserID() != null)
			return bloodSugar.getEntryID();
		else
			try {
				return bloodSugarDao.getIdFromDatabase(bloodSugar);
			} catch (DataAccessException e) {
				throw new TrackerServiceException(
						"Error has occurred while adding a blood pressure entry",
						e);
			}
	}

}
