package phr.sns.datamining.dao;

import java.util.List;

import phr.exceptions.DataAccessException;
import phr.web.models.FBPost;

public interface FacebookFetcherDao {

	public List<FBPost> getAllPosts(String userAccessToken)
			throws DataAccessException;

	public List<FBPost> getFoodRelatedPosts(String userAccessToken)
			throws DataAccessException;

	public List<FBPost> getActivityRelatedPosts(String userAccessToken)
			throws DataAccessException;
}
