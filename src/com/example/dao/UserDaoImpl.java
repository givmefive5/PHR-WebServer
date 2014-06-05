package com.example.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.example.model.User;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	@Override
	public Boolean verifyUser(String username, String password) {
			String query = "SELECT COUNT(*) FROM user WHERE username = ? AND password = ?";
			
			int count = jdbcTemplate.queryForInt(query, new Object[]{username, password});
			if(count > 0)
				return true;
			else
				return false;
	}

	@Override
	public User getUserGivenUsername(String username) {
		String query = "SELECT userId, username, password, role FROM user WHERE username = ?";
		
		@SuppressWarnings("unchecked")
		List<User> user = (List<User>)jdbcTemplate.query(query, new Object[]{username}, new RowMapper() {
													public Object mapRow(ResultSet rs, int rowNum)
															throws SQLException {
																long id = rs.getLong("userId");
																String username = rs.getString("username");
																String password = rs.getString("password");
																String role = rs.getString("role");
																User user = new User(id, username, password, role);
																return user;
															}
													});
		if(user.size() > 0){
			return user.get(0);
		}
		return null;
	}
	
	@Override
	public Boolean userExists(String usernameToBeChecked) {
		String query = "SELECT COUNT(*) FROM user WHERE username = ?";
		
		int count = jdbcTemplate.queryForInt(query, new Object[]{usernameToBeChecked});
		if(count > 0)
			return true;
		else
			return false;
	}

	

}
