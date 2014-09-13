package phr.service.impl;

import java.util.ArrayList;

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
	public void add(String username, BloodSugar bloodSugar)
			throws TrackerServiceException {
		try {
			int userID = userDao.getUserIdGivenUsername(username);
			bloodSugar.setUserID(userID);
			bloodSugarDao.add(bloodSugar);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new TrackerServiceException(
					"Error has occurred while adding a blood sugar entry", e);
		}
	}

	@Override
	public void edit(String username, BloodSugar object)
			throws TrackerServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String username, BloodSugar object)
			throws TrackerServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<BloodSugar> getAll(String username)
			throws TrackerServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getEntryId(BloodSugar bloodSugar)
			throws TrackerServiceException {
		if (bloodSugar.getUserID() != null)
			return bloodSugar.getEntryID();
		else
			try {
				return bloodSugarDao.getEntryId(bloodSugar);
			} catch (DataAccessException e) {
				throw new TrackerServiceException(
						"Error has occurred while adding a blood sugar entry",
						e);
			}
	}

}
