package phr.dao;

import java.util.List;

import phr.exceptions.DataAccessException;
import phr.models.FBPost;
import phr.models.UnverifiedActivityEntry;
import phr.models.UnverifiedFoodEntry;
import phr.models.UnverifiedRestaurantEntry;
import phr.models.UnverifiedSportsEstablishmentEntry;

public interface VerificationDao {

	public void addNewUnverifiedPosts(String userAccessToken,
			List<FBPost> newFbPosts) throws DataAccessException;

	public void delete(FBPost fbPost);

	public List<UnverifiedFoodEntry> getAllUnverifiedFoodPosts(
			String userAccessToken);

	public List<UnverifiedActivityEntry> getAllUnverifiedActivityPosts(
			String userAccessToken);

	public List<UnverifiedRestaurantEntry> getAllUnverifiedRestaurantPosts(
			String userAccessToken);

	public List<UnverifiedSportsEstablishmentEntry> getAllUnverifiedSportsEstablishmentPosts(
			String userAccessToken);

}
