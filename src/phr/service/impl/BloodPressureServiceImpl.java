package phr.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.dao.BloodPressureDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.TrackerServiceException;
import phr.service.BloodPressureService;
import phr.web.models.BloodPressure;
import phr.web.models.User;

@Service("bloodPressureService")
public class BloodPressureServiceImpl implements BloodPressureService {

	@Autowired
	BloodPressureDao bloodPressureDao;

	@Autowired
	UserDao userDao;

	@Override
	public void add(String accessToken, BloodPressure bloodPressure)
			throws TrackerServiceException {
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			bloodPressure.setUser(new User(userID));
			bloodPressureDao.add(bloodPressure);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new TrackerServiceException(
					"Error has occurred while adding a blood pressure entry", e);
		}
	}

	@Override
	public void edit(String accessToken, BloodPressure object)
			throws TrackerServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String accessToken, BloodPressure object)
			throws TrackerServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<BloodPressure> getAll(String accessToken)
			throws TrackerServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getEntryId(BloodPressure bloodPressure)
			throws TrackerServiceException {
		if (bloodPressure.getEntryID() != null)
			return bloodPressure.getEntryID();
		else
			try {
				return bloodPressureDao.getEntryId(bloodPressure);
			} catch (DataAccessException e) {
				throw new TrackerServiceException(
						"Error has occurred while adding a blood pressure entry",
						e);
			}
	}

}
