package phr.dao;

import java.util.List;

import phr.exceptions.DataAccessException;
import phr.models.FBPost;

public interface FacebookPostDao {
	
	public List<String> getAllFacebookID (String AccessToken) throws DataAccessException;
}
