package phr.service;

import java.util.List;

import phr.models.FBPost;

public interface VerificationService {

	public void addNewUnverifiedPosts(String userAccessToken,
			List<FBPost> newFbPosts);

	public void delete(FBPost fbPost);

	public List<FBPost> getAllUnverifiedFoodPosts(String userAccessToken);

	public List<FBPost> getAllUnverifiedActivityPosts(String userAccessToken);

	public List<FBPost> getAllUnverifiedRestaurantPosts(String userAccessToken);

	public List<FBPost> getAllUnverifiedSportsEstablishmentPosts(
			String userAccessToken);
}
