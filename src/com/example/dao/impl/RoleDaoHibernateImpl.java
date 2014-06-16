package com.example.dao.impl;

import org.springframework.stereotype.Repository;

import com.example.dao.RoleDao;
import com.example.model.Role;

@Repository("roleDao")
public class RoleDaoHibernateImpl extends BaseDAOHibernateImpl<Role, Long>
		implements RoleDao {

	protected RoleDaoHibernateImpl() {
		super(Role.class);
	}

}
