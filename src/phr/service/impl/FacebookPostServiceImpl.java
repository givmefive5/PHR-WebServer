package phr.service.impl;

import java.sql.Timestamp;
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
	public List<String> getAllFacebookID(String AccessToken)
			throws ServiceException {
		List<String> facebookIDList = new ArrayList<String>();

		try {
			facebookIDList = facebookPostDao.getAllFacebookID(AccessToken);
			facebookIDList.addAll(facebookPostDao
					.getAllDeletedFacebokID(AccessToken));
		} catch (DataAccessException e) {
			throw new ServiceException(
					"Error has occured while searching for facebookID", e);
		}
		return facebookIDList;
	}

	@Override
	public Timestamp getLatestPostTimestamp(String accessToken)
			throws ServiceException {
		try {
			return facebookPostDao.getLatestTimestamp(accessToken);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"Error has occured while searching for facebookID", e);
		}
	}

}
