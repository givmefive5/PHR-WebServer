package phr.service;

import phr.exceptions.UserServiceException;
import phr.exceptions.UsernameAlreadyExistsException;
import phr.models.User;

public interface UserService {

	public void addUser(User user) throws UsernameAlreadyExistsException,
			UserServiceException;

	public void deleteUser(User newUser);

	public boolean isValidUser(User userToValidate) throws UserServiceException;

	public void assignAccessToken(String username, String accessToken)
			throws UserServiceException;

	public boolean isValidAccessToken(String accessToken, String username)
			throws UserServiceException;

	public int getUserIDGivenAccessToken();

}
