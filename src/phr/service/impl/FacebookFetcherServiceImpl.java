package phr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.exceptions.DataAccessException;
import phr.exceptions.SNSException;
import phr.service.FacebookFetcherService;
import phr.sns.datamining.dao.FacebookFetcherDao;
import phr.web.models.FBPost;

@Service("facebookFetcherService")
public class FacebookFetcherServiceImpl implements FacebookFetcherService {

	@Autowired
	FacebookFetcherDao facebookFetcherDao;

	@Override
	public List<FBPost> getAllPosts(String userAccessToken) throws SNSException {
		// TODO Auto-generated method stub
		try {
			return facebookFetcherDao.getAllPosts(userAccessToken);
		} catch (DataAccessException e) {
			throw new SNSException("An error has occured", e);
		}
	}

	@Override
	public ArrayList<FBPost> getFoodRelatedPosts(String userAccessToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<FBPost> getActivityRelatedPosts(String userAccessToken) {
		// TODO Auto-generated method stub
		return null;
	}

}
