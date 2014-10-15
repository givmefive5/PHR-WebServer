package phr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.dao.VerificationDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
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
	public void delete(FBPost fbPost) throws ServiceException {
		try {
			verificationDao.delete(fbPost);
		} catch (EntryNotFoundException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while deleting a activity entry", e);
		}
	}

	@Override
	public List<UnverifiedFoodEntry> getAllUnverifiedFoodPosts(
			String userAccessToken) throws ServiceException {
		try {
			return verificationDao.getAllUnverifiedFoodPosts(userAccessToken);
		} catch (DataAccessException e) {
			throw new ServiceException("Unable to perform action", e);
		}
	}

	@Override
	public List<UnverifiedActivityEntry> getAllUnverifiedActivityPosts(
			String userAccessToken) throws ServiceException {
		try {
			return verificationDao.getAllUnverifiedActivityPosts(userAccessToken);
		} catch (DataAccessException e) {
			throw new ServiceException("Unable to perform action", e);
		}
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
