package phr.sns.datamining.service;

import java.sql.Timestamp;
import java.util.List;

import phr.exceptions.SNSException;
import phr.web.models.FBPost;

public interface FacebookFetcherService {

	public List<FBPost> getAllPosts(String userFBAccessToken)
			throws SNSException;

	public List<FBPost> getNewPostsAfterDate(Timestamp timestamp,
			String userFBAccessToken) throws SNSException;

}
