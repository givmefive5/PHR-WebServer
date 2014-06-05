package com.example.service;

import com.example.model.User;

public interface UserService {

	public Boolean verifyUser(String username, String password);

	public Boolean userExists(String usernameToBeChecked);

	public User getUserGivenUsername(String username);

}
