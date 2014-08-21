package com.example.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.example.dao.UserDao;
import com.example.exceptions.DataAccessException;
import com.example.exceptions.UsernameAlreadyExistsException;
import com.example.model.User;
import com.example.tools.Hasher;

@Repository("userDao")
public class UserDaoSqlImpl extends BaseDaoSqlImpl implements UserDao {

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

		return (count == 1) ? true : false;
	}

	@Override
	public void assignAccessToken(String username, String accessToken)
			throws DataAccessException {

		String hashedAccessToken = Hasher.hashString(accessToken);
		System.out.println("Assigning: Original : " + accessToken);
		System.out.println("Assigning: Hashed: " + hashedAccessToken);
		Connection conn = getConnection();

		String query = "UPDATE user SET accessToken = ? WHERE username = ?";
		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, hashedAccessToken);
			pstmt.setString(2, username);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

	}

	@Override
	public void addUser(User user) throws UsernameAlreadyExistsException,
			DataAccessException {
		// TODO Auto-generated method stub
		if (usernameAlreadyExists(user.getUsername()))
			throw new UsernameAlreadyExistsException(
					"Username already exists, cannot perform registration successfully");
		else {
			try {
				Connection conn = getConnection();
				String query = "INSERT INTO user(username, password) VALUES (?, ?)";
				PreparedStatement pstmt;

				String hashedPassword = Hasher.hashString(user.getPassword());
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, user.getUsername());
				pstmt.setString(2, hashedPassword);

				pstmt.executeUpdate();

			} catch (SQLException e) {
				throw new DataAccessException(
						"An error has occured while trying to access data from the database",
						e);
			}
		}

	}

	private boolean usernameAlreadyExists(String username)
			throws DataAccessException {

		int count = 0;
		try {
			Connection conn = getConnection();
			String query = "SELECT COUNT(*) FROM user " + "WHERE username = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);

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

		return (count == 1) ? true : false;
	}

	@Override
	public boolean isValidAccessToken(String accessToken, String username)
			throws DataAccessException {
		int count = 0;

		String hashedAccessToken = Hasher.hashString(accessToken);

		try {
			System.out.println("Checking: Original : " + accessToken);
			System.out.println("Checking: Hashed : " + hashedAccessToken);
			Connection conn = getConnection();
			String query = "SELECT COUNT(*) FROM user "
					+ "WHERE accessToken = ? AND username = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, hashedAccessToken);
			pstmt.setString(2, username);

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

		return (count == 1) ? true : false;
	}

	@Override
	public int getUserIdGivenUsername(String username)
			throws DataAccessException {
		try {
			Connection conn = getConnection();
			String query = "SELECT id FROM user " + "WHERE username = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);

			ResultSet rs = pstmt.executeQuery();
			int id;
			if (rs.next()) {
				id = rs.getInt(1);
				pstmt.close();
				conn.close();
				return id;
			} else {
				pstmt.close();
				conn.close();
				throw new DataAccessException("Missing userID");
			}

		} catch (SQLException | DataAccessException e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
	}
}
