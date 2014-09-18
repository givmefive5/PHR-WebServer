package phr.dao;

import phr.web.models.FBPost;

public interface FacebookPostDao extends TrackerDao<FBPost> {

	public FBPost get(int entryID);
}
