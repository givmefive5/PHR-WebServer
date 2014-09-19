package phr.sns.service;

import java.util.List;

import phr.exceptions.ServiceException;
import phr.web.models.FBPost;

public interface FacebookFetcherAndFilterService {
	public List<FBPost> getAllPosts(String userAccessToken)
			throws ServiceException;

	public List<FBPost> getFoodRelatedPosts(String userAccessToken)
			throws ServiceException;

	public List<FBPost> getActivityRelatedPosts(String userAccessToken)
			throws ServiceException;
}
