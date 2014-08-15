package com.example.dao;

import com.example.exceptions.DataAccessException;
import com.example.model.User;

public interface UserDao {

	public boolean userWithUsernameExists(String username);

	public boolean isValidUser(User user) throws DataAccessException;

}
