package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.UserDao;
import com.example.model.User;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	@Override
	public Boolean verifyUser(String username, String password) {
		return userDao.verifyUser(username, password);
	}

	@Override
	public Boolean userExists(String usernameToBeChecked) {
		return userDao.userExists(usernameToBeChecked);
	}

	@Override
	public User getUserGivenUsername(String username) {
		return userDao.getUserGivenUsername(username);
	}

}
