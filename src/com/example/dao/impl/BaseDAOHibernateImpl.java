package com.example.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.Dao;

@Transactional
public abstract class BaseDAOHibernateImpl<T, ID extends Serializable>
		implements Dao<T, ID> {

	private final Class<T> domainClass;

	@Autowired
	protected SessionFactory sessionFactory;

	protected BaseDAOHibernateImpl(Class<T> domainClass) {
		this.domainClass = domainClass;
	}

	@SuppressWarnings("unchecked")
	public List<T> list() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(domainClass);
		List<T> result = criteria.list();
		return result;
	}

	@SuppressWarnings("unchecked")
	public T get(ID id) {
		Session session = sessionFactory.getCurrentSession();
		T obj = (T) session.get(domainClass, id);
		return obj;
	}

	public void save(T object) {
		Session session = sessionFactory.getCurrentSession();
		session.save(object);
	}

	public void update(T object) {
		Session session = sessionFactory.getCurrentSession();
		session.update(object);
	}

	public void merge(T object) {
		Session session = sessionFactory.getCurrentSession();
		session.merge(object);
	}

	public void delete(T object) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(object);
	}

	public Boolean isEmpty() {
		return list().isEmpty();
	}
}