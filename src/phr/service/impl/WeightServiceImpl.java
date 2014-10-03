package phr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.dao.UserDao;
import phr.dao.WeightDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.exceptions.ServiceException;
import phr.service.WeightService;
import phr.web.models.User;
import phr.web.models.Weight;

@Service("weightService")
public class WeightServiceImpl implements WeightService {
	
	@Autowired
	WeightDao weightDao;

	@Autowired
	UserDao userDao;

	@Override
	public int addReturnEntryID(String accessToken, Weight weight) throws ServiceException {
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			weight.setUser(new User(userID));
			return weightDao.addReturnsEntryID(weight);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while adding a weight entry", e);
		}
	}

	@Override
	public void edit(String accessToken, Weight weight) throws ServiceException, EntryNotFoundException {
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			weight.setUser(new User(userID));
			weightDao.edit(weight);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while editing a weight entry", e);
		}

	}

	@Override
	public void delete(String accessToken, Weight weight)
			throws ServiceException, EntryNotFoundException {
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			weight.setUser(new User(userID));
			weightDao.delete(weight);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while deleting a weight entry", e);
		}
	}

	@Override
	public List<Weight> getAll(String accessToken) throws ServiceException {
		
		List<Weight> weights = new ArrayList<Weight>();
		
		try{
			weights = weightDao.getAll(accessToken);
		}catch(DataAccessException e){
			e.printStackTrace();
			throw new ServiceException(
					"Error has occured while getting all entries in weight tracker", e);
		}
		
		return weights;
	}

	@Override
	public Integer getEntryId(Weight weight) throws ServiceException {
		if (weight.getUser() != null)
			return weight.getEntryID();
		else
			try {
				return weightDao.getEntryId(weight);
			} catch (DataAccessException e) {
				throw new ServiceException(
						"Error has occurred while getting the entry id of a weight entry",
						e);
			}
	}

}
