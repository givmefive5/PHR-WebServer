package phr.dao;

import phr.exceptions.DataAccessException;
import phr.exceptions.UsernameAlreadyExistsException;
import phr.models.User;

public interface UserDao {

	public boolean isValidUser(User user) throws DataAccessException;

	public void assignAccessToken(String username, String accessToken)
			throws DataAccessException;

	public void addUser(User user) throws UsernameAlreadyExistsException,
			DataAccessException;

	public boolean isValidAccessToken(String accessToken, String username)
			throws DataAccessException;

	public int getUserIdGivenUsername(String username)
			throws DataAccessException;

	public Integer getUserIDGivenAccessToken(String userAccessToken)
			throws DataAccessException;

	public boolean usernameAlreadyExists(String username)
			throws DataAccessException;

	public User getUserGivenAccessToken(String accessToken);
}
