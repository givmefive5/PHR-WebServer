package com.example.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.example.dao.UserDao;
import com.example.model.User;

@Repository("userDao")
public class UserDAOHibernateImpl extends BaseDAOHibernateImpl<User, Long>
		implements UserDao {

	protected UserDAOHibernateImpl() {
		super(User.class);
	}

	public boolean userWithUsernameExists(String username) {
		String hql = "from User where username = :username";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		sessionFactory.getCurrentSession().clear();
		query.setParameter("username", username);

		List<User> result = query.list();
		return !result.isEmpty();
	}

	@Override
	public boolean isValidUser(User userToValidate) {
		String hql = "from User where username = :username AND password = :password";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		sessionFactory.getCurrentSession().clear();
		System.out.println(userToValidate.getUsername() + " "
				+ userToValidate.getPassword());
		query.setParameter("username", userToValidate.getUsername());
		query.setParameter("password", userToValidate.getPassword());

		List<User> result = query.list();
		System.out.println(result.size());
		return !result.isEmpty();
	}

}
