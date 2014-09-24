package phr.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.dao.CheckUpDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.exceptions.ServiceException;
import phr.service.CheckUpService;
import phr.web.models.CheckUp;
import phr.web.models.User;

@Service("checkUpService")
public class CheckUpServiceImpl implements CheckUpService {
	
	@Autowired
	CheckUpDao checkUpDao;

	@Autowired
	UserDao userDao;

	@Override
	public void add(String accessToken, CheckUp checkUp) throws ServiceException {
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			checkUp.setUser(new User(userID));
			checkUpDao.add(checkUp);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while adding a checkup entry", e);
		}
	}

	@Override
	public void edit(String accessToken, CheckUp checkUp)
			throws ServiceException, EntryNotFoundException {
		
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			checkUp.setUser(new User(userID));
			checkUpDao.edit(checkUp);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while editing a check up entry", e);
		}

	}

	@Override
	public void delete(String accessToken, CheckUp checkUp)
			throws ServiceException, EntryNotFoundException {
		
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			checkUp.setUser(new User(userID));
			checkUpDao.delete(checkUp);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while deleting a check up entry", e);
		}

	}

	@Override
	public ArrayList<CheckUp> getAll(String accessToken)
			throws ServiceException {
		
		ArrayList<CheckUp> checkups = new ArrayList<CheckUp>();
		
		try{
			checkups = checkUpDao.getAll(accessToken);
		}catch(DataAccessException e){
			e.printStackTrace();
			throw new ServiceException(
					"Error has occured while getting all entries in Check Up tracker", e);
		}
		
		return checkups;
	}

	@Override
	public Integer getEntryId(CheckUp checkUp) throws ServiceException {
		if (checkUp.getUser() != null)
			return checkUp.getEntryID();
		else
			try {
				return checkUpDao.getEntryId(checkUp);
			} catch (DataAccessException e) {
				throw new ServiceException(
						"Error has occurred while getting the entry id of a checkup entry",
						e);
			}
	}
}
