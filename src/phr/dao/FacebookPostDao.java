package phr.dao;

import phr.models.FBPost;

public interface FacebookPostDao extends TrackerDao<FBPost> {

	public FBPost get(int entryID);
}
