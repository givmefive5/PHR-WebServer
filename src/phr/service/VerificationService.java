package phr.service;

import java.sql.Timestamp;
import java.util.List;

import phr.exceptions.EntryNotFoundException;
import phr.exceptions.ServiceException;
import phr.models.UnverifiedActivityEntry;
import phr.models.UnverifiedFoodEntry;
import phr.models.UnverifiedRestaurantEntry;
import phr.models.UnverifiedSportsEstablishmentEntry;

public interface VerificationService {

	public void updateListOfUnverifiedPosts(String userAccessToken,
			String userFBAccessToken, Timestamp startDate)
			throws ServiceException;

	public List<UnverifiedFoodEntry> getAllUnverifiedFoodPosts(
			String userAccessToken) throws ServiceException;

	public List<UnverifiedActivityEntry> getAllUnverifiedActivityPosts(
			String userAccessToken) throws ServiceException;

	public List<UnverifiedRestaurantEntry> getAllUnverifiedRestaurantPosts(
			String userAccessToken) throws ServiceException;

	public List<UnverifiedSportsEstablishmentEntry> getAllUnverifiedSportsEstablishmentPosts(
			String userAccessToken) throws ServiceException;

	public void delete(UnverifiedFoodEntry entry)
			throws EntryNotFoundException, ServiceException;

	public void delete(UnverifiedActivityEntry entry)
			throws EntryNotFoundException, ServiceException;

	public void delete(UnverifiedRestaurantEntry entry)
			throws EntryNotFoundException, ServiceException;

	public void delete(UnverifiedSportsEstablishmentEntry entry)
			throws EntryNotFoundException, ServiceException;
}
