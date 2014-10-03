package phr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.dao.BloodPressureDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.exceptions.ServiceException;
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
	public int addReturnEntryID(String accessToken, BloodPressure bloodPressure)
			throws ServiceException {
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			bloodPressure.setUser(new User(userID));
			return bloodPressureDao.addReturnsEntryID(bloodPressure);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while adding a blood pressure entry", e);
		}
	}

	@Override
	public void edit(String accessToken, BloodPressure bloodPressure)
			throws ServiceException, EntryNotFoundException {
		
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			bloodPressure.setUser(new User(userID));
			bloodPressureDao.edit(bloodPressure);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while editing a blood pressure entry", e);
		}
	}

	@Override
	public void delete(String accessToken, BloodPressure bloodPressure)
			throws ServiceException, EntryNotFoundException {
		
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			bloodPressure.setUser(new User(userID));
			bloodPressureDao.delete(bloodPressure);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while deleting a blood pressure entry", e);
		}
	}

	@Override
	public List<BloodPressure> getAll(String accessToken)
			throws ServiceException {
		
		List<BloodPressure> bloodpressures = new ArrayList<BloodPressure>();
		
		try{
			bloodpressures = bloodPressureDao.getAll(accessToken);
		}catch(DataAccessException e){
			e.printStackTrace();
			throw new ServiceException(
					"Error has occured while getting all entries in Blood Pressure tracker", e);
		}
		
		return bloodpressures;
	}


	@Override
	public Integer getEntryId(BloodPressure bloodPressure)
			throws ServiceException {
		if (bloodPressure.getEntryID() != null)
			return bloodPressure.getEntryID();
		else
			try {
				return bloodPressureDao.getEntryId(bloodPressure);
			} catch (DataAccessException e) {
				throw new ServiceException(
						"Error has occurred while getting the entry id a blood pressure entry",
						e);
			}
	}

}
