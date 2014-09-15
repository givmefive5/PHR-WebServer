package phr.sns.datamining.dao;

import java.io.IOException;
import java.util.ArrayList;

import phr.web.models.FBPost;
import facebook4j.FacebookException;

public interface FacebookFetcherDao {

	public ArrayList<FBPost> getAllPosts(String userAccessToken)
			throws FacebookException, IOException;

	public ArrayList<FBPost> getFoodRelatedPosts(String userAccessToken);

	public ArrayList<FBPost> getActivityRelatedPosts(String userAccessToken);
}
