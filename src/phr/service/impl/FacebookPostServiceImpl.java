package phr.service.impl;

import java.util.ArrayList;
import java.util.List;

import phr.dao.FacebookPostDao;
import phr.dao.sqlimpl.FacebookPostDaoSqlImpl;
import phr.exceptions.DataAccessException;
import phr.exceptions.ServiceException;
import phr.service.FacebookPostService;

public class FacebookPostServiceImpl implements FacebookPostService {
	
	FacebookPostDao facebookPostDao = new FacebookPostDaoSqlImpl();

	@Override
	public List<String> getAllFacebookID(String AccessToken) throws ServiceException {
		List<String> facebookIDList = new ArrayList<String>();
		
		try {
			facebookIDList = facebookPostDao.getAllFacebookID(AccessToken);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"Error has occured while searching for facebookID",
					e);
		}
		return facebookIDList;
	}

}
