package phr.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import phr.dao.VerificationDao;
import phr.dao.sqlimpl.VerificationDaoImpl;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.exceptions.ServiceException;
import phr.models.FBPost;
import phr.models.UnverifiedActivityEntry;
import phr.models.UnverifiedFoodEntry;
import phr.models.UnverifiedRestaurantEntry;
import phr.models.UnverifiedSportsEstablishmentEntry;
import phr.service.VerificationService;
import phr.sns.datamining.dao.FacebookFetcherDao;
import phr.sns.datamining.daoimpl.FacebookFetcherDaoImpl;

@Service("verificationService")
public class VerificationServiceImpl implements VerificationService {

	// @Autowired
	// VerificationDao verificationDao;

	VerificationDao verificationDao = new VerificationDaoImpl();

	FacebookFetcherDao facebookFetcherDao = new FacebookFetcherDaoImpl();

	@Override
	public void updateListOfUnverifiedPosts(String userAccessToken,
			String userFBAccessToken, Timestamp startDate)
			throws ServiceException {

		try {
			List<FBPost> fbPosts = facebookFetcherDao.getNewPosts(startDate,
					userFBAccessToken, userAccessToken);

			for (FBPost p : fbPosts) {
				System.out
						.println(p.getStatus() + " Class: " + p.getPostType());
				System.out.println("Extracted Words: ");
				if (p.getExtractedWords() != null)
					for (String s : p.getExtractedWords()) {
						System.out.println(s);
					}
			}

			addNewUnverifiedPosts(userAccessToken, fbPosts);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("Error in fetching fbPosts", e);
		}
	}

	private void addNewUnverifiedPosts(String userAccessToken,
			List<FBPost> newFbPosts) throws ServiceException {
		try {
			verificationDao.addNewUnverifiedPosts(userAccessToken, newFbPosts);
		} catch (DataAccessException e) {
			throw new ServiceException("Unable to perform action", e);
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
			return verificationDao
					.getAllUnverifiedActivityPosts(userAccessToken);
		} catch (DataAccessException e) {
			throw new ServiceException("Unable to perform action", e);
		}
	}

	@Override
	public List<UnverifiedRestaurantEntry> getAllUnverifiedRestaurantPosts(
			String userAccessToken) throws ServiceException {
		try {
			return verificationDao
					.getAllUnverifiedRestaurantPosts(userAccessToken);
		} catch (DataAccessException e) {
			throw new ServiceException("Unable to perform action", e);
		}
	}

	@Override
	public List<UnverifiedSportsEstablishmentEntry> getAllUnverifiedSportsEstablishmentPosts(
			String userAccessToken) throws ServiceException {
		try {
			return verificationDao
					.getAllUnverifiedSportsEstablishmentPosts(userAccessToken);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("Unable to perform action", e);
		}
	}

	@Override
	public void delete(UnverifiedFoodEntry entry) throws ServiceException {
		try {
			verificationDao.delete(entry);
		} catch (EntryNotFoundException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while deleting an unverified food entry",
					e);
		}
	}

	@Override
	public void delete(UnverifiedActivityEntry entry) throws ServiceException {
		try {
			verificationDao.delete(entry);
		} catch (EntryNotFoundException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while deleting an unverified activity entry",
					e);
		}
	}

	@Override
	public void delete(UnverifiedRestaurantEntry entry) throws ServiceException {
		try {
			verificationDao.delete(entry);
		} catch (EntryNotFoundException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while deleting an unverified restaurant entry",
					e);
		}
	}

	@Override
	public void delete(UnverifiedSportsEstablishmentEntry entry)
			throws ServiceException {
		try {
			verificationDao.delete(entry);
		} catch (EntryNotFoundException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while deleting an unverified entry", e);
		}
	}

}
