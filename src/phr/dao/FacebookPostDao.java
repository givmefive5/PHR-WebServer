package phr.dao;

import java.sql.Timestamp;
import java.util.List;

import phr.exceptions.DataAccessException;

public interface FacebookPostDao {

	public List<String> getAllFacebookID(String AccessToken)
			throws DataAccessException;

	public Timestamp getLatestTimestamp(String AccessToken)
			throws DataAccessException;

	public List<String> getAllDeletedFacebokID(String AccessToken)
			throws DataAccessException;

	public void addDeletedFacebookID(int UserID, String FacebookID)
			throws DataAccessException;
}
