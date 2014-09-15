package phr.service.impl;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.dao.FacebookFetcherDao;
import phr.service.FacebookFetcherService;
import phr.web.models.FBPost;
import facebook4j.FacebookException;

@Service("facebookFetcherService")
public class FacebookFetcherServiceImpl implements FacebookFetcherService {

	@Autowired
	FacebookFetcherDao facebookFetcherDao;

	@Override
	public ArrayList<FBPost> getAllPosts(String userAccessToken)
			throws FacebookException, IOException {
		// TODO Auto-generated method stub
		return facebookFetcherDao.getAllPosts(userAccessToken);
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
