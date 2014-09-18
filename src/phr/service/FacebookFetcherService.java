package phr.service;

import java.util.List;

import phr.exceptions.SNSException;
import phr.web.models.FBPost;

public interface FacebookFetcherService {

	public List<FBPost> getAllPosts(String userAccessToken) throws SNSException;

	public List<FBPost> getFoodRelatedPosts(String userAccessToken);

	public List<FBPost> getActivityRelatedPosts(String userAccessToken);
}
