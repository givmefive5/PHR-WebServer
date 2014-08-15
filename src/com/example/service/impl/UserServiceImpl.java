package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.UserDao;
import com.example.exceptions.DataAccessException;
import com.example.exceptions.UserServiceException;
import com.example.exceptions.UsernameAlreadyExistsException;
import com.example.model.User;
import com.example.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Override
	public void addUser(User user) throws UsernameAlreadyExistsException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUser(User newUser) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValidUser(User userToValidate) throws UserServiceException {
		try {
			return userDao.isValidUser(userToValidate);
		} catch (DataAccessException e) {
			throw new UserServiceException("Error in validating the user", e);
		}
	}

	@Override
	public void assignAccessToken(String username, String accessToken)
			throws UserServiceException {
		try {
			userDao.assignAccessToken(username, accessToken);
		} catch (DataAccessException e) {
			throw new UserServiceException("Error in assigning access token", e);
		}
	}

}
