package phr.dao;

import java.util.List;

import phr.exceptions.DataAccessException;
import phr.models.FBPost;

public interface VerificationDao {

	public void addNewUnverifiedPosts(String userAccessToken,
			List<FBPost> newFbPosts) throws DataAccessException;

	public void delete(FBPost fbPost);

	public List<FBPost> getAllUnverifiedFoodPosts(String userAccessToken);

	public List<FBPost> getAllUnverifiedActivityPosts(String userAccessToken);

	public List<FBPost> getAllUnverifiedRestaurantPosts(String userAccessToken);

	public List<FBPost> getAllUnverifiedSportsEstablishmentPosts(
			String userAccessToken);

}
