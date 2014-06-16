package com.example.dao.impl;

import org.springframework.stereotype.Repository;

import com.example.dao.UserDao;
import com.example.model.User;

@Repository("userDao")
public class UserDAOHibernateImpl  extends BaseDAOHibernateImpl<User, Long> implements UserDao{

	protected UserDAOHibernateImpl() {
		super(User.class);
	}

}
