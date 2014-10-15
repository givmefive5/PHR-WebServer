package phr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.dao.VerificationDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.ServiceException;
import phr.models.FBPost;
import phr.service.VerificationService;

@Service("verificationService")
public class VerificationServiceImpl implements VerificationService {

	@Autowired
	VerificationDao verificationDao;

	@Override
	public void addNewUnverifiedPosts(String userAccessToken,
			List<FBPost> newFbPosts) throws ServiceException {
		try {
			verificationDao.addNewUnverifiedPosts(userAccessToken, newFbPosts);
		} catch (DataAccessException e) {
			throw new ServiceException("Unable to perform action", e);
		}
	}

	@Override
	public void delete(FBPost fbPost) {
		verificationDao.delete(fbPost);
	}

	@Override
	public List<FBPost> getAllUnverifiedFoodPosts(String userAccessToken) {
		return verificationDao.getAllUnverifiedFoodPosts(userAccessToken);
	}

	@Override
	public List<FBPost> getAllUnverifiedActivityPosts(String userAccessToken) {
		return verificationDao.getAllUnverifiedActivityPosts(userAccessToken);
	}

	@Override
	public List<FBPost> getAllUnverifiedRestaurantPosts(String userAccessToken) {
		return verificationDao.getAllUnverifiedRestaurantPosts(userAccessToken);
	}

	@Override
	public List<FBPost> getAllUnverifiedSportsEstablishmentPosts(
			String userAccessToken) {
		return verificationDao
				.getAllUnverifiedSportsEstablishmentPosts(userAccessToken);
	}

}