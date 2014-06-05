package com.example.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.exceptions.DatabaseAccessException;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private String hashPassword(String password) throws DatabaseAccessException{
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new DatabaseAccessException("Hashing failed", e);
		}
	}
	
	@Override
	public boolean verifyUser(String username, String password) {
		try {
			String hashedPassword = hashPassword(password);
			String query = "SELECT COUNT(*) FROM user WHERE username = ? AND password = ?";
			
			int count = jdbcTemplate.queryForInt(query, new Object[]{username, hashedPassword});
			if(count > 0)
				return true;
			else
				return false;
		} catch (DatabaseAccessException e) {
			System.out.println("Error nga eh");
		}
		return false;
	}

}
