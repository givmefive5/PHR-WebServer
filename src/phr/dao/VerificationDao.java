package phr.dao;

import java.util.List;

import phr.models.FBPost;

public interface VerificationDao {

	public void setNewUnverifiedPosts(String userAccessToken,
			List<FBPost> newFbPosts);

}
