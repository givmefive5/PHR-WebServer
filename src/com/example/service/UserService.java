package com.example.service;

import com.example.exceptions.UserServiceException;
import com.example.exceptions.UsernameAlreadyExistsException;
import com.example.model.User;

public interface UserService {

	public void addUser(User user) throws UsernameAlreadyExistsException,
			UserServiceException;

	public void deleteUser(User newUser);

	public boolean isValidUser(User userToValidate) throws UserServiceException;

	public void assignAccessToken(String username, String accessToken)
			throws UserServiceException;

	public boolean isValidAccessToken(String accessToken)
			throws UserServiceException;

}
