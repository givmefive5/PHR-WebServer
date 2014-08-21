package com.example.dao;

import com.example.exceptions.DataAccessException;
import com.example.exceptions.UsernameAlreadyExistsException;
import com.example.model.User;

public interface UserDao {

	public boolean isValidUser(User user) throws DataAccessException;

	public void assignAccessToken(String username, String accessToken)
			throws DataAccessException;

	public void addUser(User user) throws UsernameAlreadyExistsException,
			DataAccessException;

	public boolean isValidAccessToken(String accessToken, String username)
			throws DataAccessException;

}
