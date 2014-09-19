package phr.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.dao.BloodSugarDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
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
	public void add(String accessToken, BloodSugar bloodSugar)
			throws ServiceException {
		try {
			int userID = userDao.getUserIdGivenUsername(accessToken);
			bloodSugar.setUser(new User(userID));
			bloodSugarDao.add(bloodSugar);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while adding a blood sugar entry", e);
		}
	}

	@Override
	public void edit(String accessToken, BloodSugar object)
			throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String accessToken, BloodSugar object)
			throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<BloodSugar> getAll(String accessToken)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
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
						"Error has occurred while adding a blood sugar entry",
						e);
			}
	}

}
