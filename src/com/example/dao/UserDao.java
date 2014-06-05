package com.example.dao;

import com.example.model.User;

public interface UserDao {

	public Boolean verifyUser(String username, String password);

	public Boolean userExists(String usernameToBeChecked);

	public User getUserGivenUsername(String username);
}
