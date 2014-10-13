package phr.sns.datamining.dao;

import java.sql.Timestamp;
import java.util.List;

import phr.exceptions.DataAccessException;
import phr.models.FBPost;

public interface FacebookFetcherDao {

	public List<FBPost> getAllPosts(String userFBAccessToken)
			throws DataAccessException;

	public List<FBPost> getNewPostsAfterDate(Timestamp timestamp,
			String userFBAccessToken) throws DataAccessException;
}
