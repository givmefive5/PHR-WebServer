package phr.sns.datamining.dao;

import java.util.ArrayList;

import phr.exceptions.DataAccessException;
import phr.web.models.FBPost;

public interface FacebookFetcherDao {

	public ArrayList<FBPost> getAllPosts(String userAccessToken)
			throws DataAccessException;

	public ArrayList<FBPost> getFoodRelatedPosts(String userAccessToken);

	public ArrayList<FBPost> getActivityRelatedPosts(String userAccessToken);
}
