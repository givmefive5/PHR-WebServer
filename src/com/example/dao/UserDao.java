package com.example.dao;

import com.example.model.User;

public interface UserDao extends Dao<User, Long> {

	public boolean userWithUsernameExists(String username);

	public boolean isValidUser(User userToValidate);

}
