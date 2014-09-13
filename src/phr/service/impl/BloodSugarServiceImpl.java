package phr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import phr.dao.BloodSugarDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.TrackerServiceException;
import phr.service.BloodSugarService;
import phr.web.models.BloodSugar;

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
