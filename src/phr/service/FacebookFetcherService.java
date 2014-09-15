package phr.service;

import java.util.ArrayList;

import phr.exceptions.SNSException;
import phr.web.models.FBPost;

public interface FacebookFetcherService {

	public ArrayList<FBPost> getAllPosts(String userAccessToken)
			throws SNSException;

	public ArrayList<FBPost> getFoodRelatedPosts(String userAccessToken);

	public ArrayList<FBPost> getActivityRelatedPosts(String userAccessToken);
}
