package com.example.dao;

import com.example.model.User;

public interface UserDao {

	public boolean userWithUsernameExists(String username);

	public boolean isValidUser(User userToValidate);

}
