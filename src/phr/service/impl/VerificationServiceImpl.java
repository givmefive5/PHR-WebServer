package phr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.dao.VerificationDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.ServiceException;
import phr.models.FBPost;
import phr.models.UnverifiedActivityEntry;
import phr.models.UnverifiedFoodEntry;
import phr.models.UnverifiedRestaurantEntry;
import phr.models.UnverifiedSportsEstablishmentEntry;
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
	public List<UnverifiedFoodEntry> getAllUnverifiedFoodPosts(
			String userAccessToken) {
		return verificationDao.getAllUnverifiedFoodPosts(userAccessToken);
	}

	@Override
	public List<UnverifiedActivityEntry> getAllUnverifiedActivityPosts(
			String userAccessToken) {
		return verificationDao.getAllUnverifiedActivityPosts(userAccessToken);
	}

	@Override
	public List<UnverifiedRestaurantEntry> getAllUnverifiedRestaurantPosts(
			String userAccessToken) {
		return verificationDao.getAllUnverifiedRestaurantPosts(userAccessToken);
	}

	@Override
	public List<UnverifiedSportsEstablishmentEntry> getAllUnverifiedSportsEstablishmentPosts(
			String userAccessToken) {
		return verificationDao
				.getAllUnverifiedSportsEstablishmentPosts(userAccessToken);
	}

}
