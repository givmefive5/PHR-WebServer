package phr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.dao.CheckUpTrackerDao;
import phr.dao.UserDao;
import phr.dao.sqlimpl.CheckUpTrackerDaoSqlImpl;
import phr.dao.sqlimpl.UserDaoSqlImpl;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.exceptions.ServiceException;
import phr.models.CheckUp;
import phr.models.User;
import phr.service.CheckUpTrackerService;

@Service("checkUpTrackerService")
public class CheckUpTrackerServiceImpl implements CheckUpTrackerService {
	
	//@Autowired
	//CheckUpTrackerDao checkUpTrackerDao;

	//@Autowired
	//UserDao userDao;
	
	UserDao userDao = new UserDaoSqlImpl();
	CheckUpTrackerDao checkUpTrackerDao = new CheckUpTrackerDaoSqlImpl();

	@Override
	public int addReturnEntryID(String accessToken, CheckUp checkUp) throws ServiceException {
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			checkUp.setUser(new User(userID));
			return checkUpTrackerDao.addReturnsEntryID(checkUp);
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
			checkUpTrackerDao.edit(checkUp);
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
			checkUpTrackerDao.delete(checkUp);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while deleting a check up entry", e);
		}

	}

	@Override
	public List<CheckUp> getAll(String accessToken)
			throws ServiceException {
		
		List<CheckUp> checkups = new ArrayList<CheckUp>();
		
		try{
			checkups = checkUpTrackerDao.getAll(accessToken);
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
				return checkUpTrackerDao.getEntryId(checkUp);
			} catch (DataAccessException e) {
				throw new ServiceException(
						"Error has occurred while getting the entry id of a checkup entry",
						e);
			}
	}
}
