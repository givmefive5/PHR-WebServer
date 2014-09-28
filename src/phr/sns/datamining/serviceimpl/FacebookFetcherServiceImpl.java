package phr.sns.datamining.serviceimpl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import phr.exceptions.DataAccessException;
import phr.exceptions.SNSException;
import phr.sns.datamining.dao.FacebookFetcherDao;
import phr.sns.datamining.daoimpl.FacebookFetcherDaoImpl;
import phr.sns.datamining.service.FacebookFetcherService;
import phr.web.models.FBPost;

@Service("facebookFetcherService")
public class FacebookFetcherServiceImpl implements FacebookFetcherService {

	FacebookFetcherDao facebookFetcherDao = new FacebookFetcherDaoImpl();

	@Override
	public List<FBPost> getAllPosts(String userFBAccessToken)
			throws SNSException {
		try {
			return facebookFetcherDao.getAllPosts(userFBAccessToken);
		} catch (DataAccessException e) {
			throw new SNSException("An error has occured", e);
		}
	}

	@Override
	public List<FBPost> getNewPostsAfterDate(Timestamp timestamp,
			String userFBAccessToken) throws SNSException {
		try {
			return facebookFetcherDao.getNewPostsAfterDate(timestamp,
					userFBAccessToken);
		} catch (DataAccessException e) {
			throw new SNSException("An error has occured", e);
		}
	}
}
