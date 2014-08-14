package com.example.dao.sqlimpl;

import com.example.dao.UserDao;
import com.example.model.User;

public class UserDaoSqlImpl extends BaseDaoSqlImpl implements UserDao {

	@Override
	public boolean userWithUsernameExists(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isValidUser(User userToValidate) {
		// TODO Auto-generated method stub
		return false;
	}

}
