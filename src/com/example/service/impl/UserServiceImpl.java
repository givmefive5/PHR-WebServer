package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.UserDao;
import com.example.exceptions.UsernameAlreadyExistsException;
import com.example.model.User;
import com.example.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Override
	public void addUser(User user) throws UsernameAlreadyExistsException {
		if (userDao.userWithUsernameExists(user.getUsername())) {
			throw new UsernameAlreadyExistsException(
					"The username to be registered already exists");
		}
		userDao.save(user);
	}

	@Override
	public void deleteUser(User newUser) {
		userDao.delete(newUser);
	}

	@Override
	public boolean isValidUser(User userToValidate) {
		return userDao.isValidUser(userToValidate);
	}

}
