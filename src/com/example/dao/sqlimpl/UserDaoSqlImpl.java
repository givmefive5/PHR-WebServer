package com.example.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.example.dao.UserDao;
import com.example.exceptions.DataAccessException;
import com.example.model.User;

@Repository("userDao")
public class UserDaoSqlImpl extends BaseDaoSqlImpl implements UserDao {

	@Override
	public boolean userWithUsernameExists(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isValidUser(User user) throws DataAccessException {
		int count = 0;

		try {
			Connection conn = getConnection();
			String query = "SELECT COUNT(*) FROM user "
					+ "WHERE username = ? AND password = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());

			ResultSet rs = pstmt.executeQuery();

			rs.next();
			count = rs.getInt(1);
			pstmt.close();
			conn.close();
		} catch (SQLException | DataAccessException e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

		if (count == 1)
			return true;
		else
			return false;
	}

	@Override
	public void assignAccessToken(String username, String accessToken) {
		// TODO Auto-generated method stub

	}

}
