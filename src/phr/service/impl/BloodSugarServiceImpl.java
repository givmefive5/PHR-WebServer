package phr.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.dao.BloodSugarDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.exceptions.ServiceException;
import phr.service.BloodSugarService;
import phr.web.models.BloodSugar;
import phr.web.models.User;

@Service("bloodSugarService")
public class BloodSugarServiceImpl implements BloodSugarService {

	@Autowired
	BloodSugarDao bloodSugarDao;

	@Autowired
	UserDao userDao;

	@Override
	public int addReturnEntryID(String accessToken, BloodSugar bloodSugar)
			throws ServiceException {
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			bloodSugar.setUser(new User(userID));
			return bloodSugarDao.addReturnsEntryID(bloodSugar);
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
			bloodSugarDao.edit(bloodSugar);
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
			bloodSugarDao.delete(bloodSugar);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while deleting a blood sugar entry", e);
		}

	}

	@Override
	public ArrayList<BloodSugar> getAll(String accessToken)
			throws ServiceException {
		ArrayList<BloodSugar> bloodsugars = new ArrayList<BloodSugar>();
		
		try{
			bloodsugars = bloodSugarDao.getAll(accessToken);
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
				return bloodSugarDao.getEntryId(bloodSugar);
			} catch (DataAccessException e) {
				throw new ServiceException(
						"Error has occurred while getting the entry id of a blood sugar entry",
						e);
			}
	}

}
