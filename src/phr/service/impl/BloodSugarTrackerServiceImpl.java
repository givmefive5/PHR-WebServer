package phr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.dao.BloodSugarTrackerDao;
import phr.dao.UserDao;
import phr.dao.sqlimpl.BloodSugarTrackerDaoSqlImpl;
import phr.dao.sqlimpl.UserDaoSqlImpl;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.exceptions.ServiceException;
import phr.models.BloodSugar;
import phr.models.User;
import phr.service.BloodSugarTrackerService;

@Service("bloodSugarTrackerService")
public class BloodSugarTrackerServiceImpl implements BloodSugarTrackerService {

	//@Autowired
	//BloodSugarTrackerDao bloodSugarTrackerDao;

	//@Autowired
	//UserDao userDao;
	
	UserDao userDao = new UserDaoSqlImpl();
	BloodSugarTrackerDao bloodSugarTrackerDao = new BloodSugarTrackerDaoSqlImpl();

	@Override
	public int addReturnEntryID(String accessToken, BloodSugar bloodSugar)
			throws ServiceException {
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			bloodSugar.setUser(new User(userID));
			return bloodSugarTrackerDao.addReturnsEntryID(bloodSugar);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while adding a blood sugar entry", e);
		}
	}

	@Override
	public void edit(String accessToken, BloodSugar bloodSugar)
			throws ServiceException, EntryNotFoundException {
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			bloodSugar.setUser(new User(userID));
			bloodSugarTrackerDao.edit(bloodSugar);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while editing a blood sugar entry", e);
		}

	}

	@Override
	public void delete(String accessToken, BloodSugar bloodSugar)
			throws ServiceException, EntryNotFoundException {
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			bloodSugar.setUser(new User(userID));
			bloodSugarTrackerDao.delete(bloodSugar);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while deleting a blood sugar entry", e);
		}

	}

	@Override
	public List<BloodSugar> getAll(String accessToken)
			throws ServiceException {
		List<BloodSugar> bloodsugars = new ArrayList<BloodSugar>();
		
		try{
			bloodsugars = bloodSugarTrackerDao.getAll(accessToken);
		}catch(DataAccessException e){
			e.printStackTrace();
			throw new ServiceException(
					"Error has occured while getting all entries in Blood Sugar tracker", e);
		}
		
		return bloodsugars;
	}

	@Override
	public Integer getEntryId(BloodSugar bloodSugar)
			throws ServiceException {
		if (bloodSugar.getUser() != null)
			return bloodSugar.getEntryID();
		else
			try {
				return bloodSugarTrackerDao.getEntryId(bloodSugar);
			} catch (DataAccessException e) {
				throw new ServiceException(
						"Error has occurred while getting the entry id of a blood sugar entry",
						e);
			}
	}

}
