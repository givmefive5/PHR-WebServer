package phr.dao;

import java.util.List;

import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.models.FBPost;
import phr.models.UnverifiedActivityEntry;
import phr.models.UnverifiedFoodEntry;
import phr.models.UnverifiedRestaurantEntry;
import phr.models.UnverifiedSportsEstablishmentEntry;

public interface VerificationDao {

	public void addNewUnverifiedPosts(String userAccessToken,
			List<FBPost> newFbPosts) throws DataAccessException;

	public List<UnverifiedFoodEntry> getAllUnverifiedFoodPosts(
			String userAccessToken) throws DataAccessException;

	public List<UnverifiedActivityEntry> getAllUnverifiedActivityPosts(
			String userAccessToken) throws DataAccessException;

	public List<UnverifiedRestaurantEntry> getAllUnverifiedRestaurantPosts(
			String userAccessToken) throws DataAccessException;

	public List<UnverifiedSportsEstablishmentEntry> getAllUnverifiedSportsEstablishmentPosts(
			String userAccessToken);

	public void delete(UnverifiedFoodEntry entry) throws EntryNotFoundException;

	public void delete(UnverifiedActivityEntry entry) throws EntryNotFoundException;

	public void delete(UnverifiedRestaurantEntry entry) throws EntryNotFoundException;

	public void delete(UnverifiedSportsEstablishmentEntry entry) throws EntryNotFoundException;

}
