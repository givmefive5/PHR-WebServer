package phr.service;

import java.util.List;

import phr.exceptions.ServiceException;
import phr.models.FBPost;
import phr.models.UnverifiedActivityEntry;
import phr.models.UnverifiedFoodEntry;
import phr.models.UnverifiedRestaurantEntry;
import phr.models.UnverifiedSportsEstablishmentEntry;

public interface VerificationService {

	public void addNewUnverifiedPosts(String userAccessToken,
			List<FBPost> newFbPosts) throws ServiceException;

	public void delete(FBPost fbPost);

	public List<UnverifiedFoodEntry> getAllUnverifiedFoodPosts(
			String userAccessToken) throws ServiceException;

	public List<UnverifiedActivityEntry> getAllUnverifiedActivityPosts(
			String userAccessToken) throws ServiceException;

	public List<UnverifiedRestaurantEntry> getAllUnverifiedRestaurantPosts(
			String userAccessToken) throws ServiceException;

	public List<UnverifiedSportsEstablishmentEntry> getAllUnverifiedSportsEstablishmentPosts(
			String userAccessToken) throws ServiceException;
}
