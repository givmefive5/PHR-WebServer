package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import phr.dao.UserDao;
import phr.dao.WeightTrackerDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.UsernameAlreadyExistsException;
import phr.models.User;
import phr.tools.Hasher;

@Repository("userDao")
public class UserDaoSqlImpl extends BaseDaoSqlImpl implements UserDao {
	
	WeightTrackerDao weightDao = new WeightTrackerDaoSqlImpl();

	@Override
	public boolean isValidUser(User user) throws DataAccessException {
		int count = 0;

		try {
			Connection conn = getConnection();
			String query = "SELECT COUNT(*) FROM useraccountandinfo "
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

		Connection conn = getConnection();

		String query = "UPDATE useraccountandinfo SET userAccessToken = ? WHERE username = ?";
		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, accessToken);
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
		if (usernameAlreadyExists(user.getUsername()))
			throw new UsernameAlreadyExistsException(
					"Username already exists, cannot perform registration successfully");
		else {
			try {
				Connection conn = getConnection();
				String query = "INSERT INTO useraccountandinfo(username, password) VALUES (?, ?)";
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

	@Override
	public boolean usernameAlreadyExists(String username)
			throws DataAccessException {

		int count = 0;
		try {
			Connection conn = getConnection();
			String query = "SELECT COUNT(*) FROM useraccountandinfo "
					+ "WHERE username = ?";
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

		try {
			Connection conn = getConnection();
			String query = "SELECT COUNT(*) FROM useraccountandinfo "
					+ "WHERE userAccessToken = ? AND username = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, accessToken);
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
			String query = "SELECT userID FROM useraccountandinfo "
					+ "WHERE username = ?";
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

	@Override
	public Integer getUserIDGivenAccessToken(String userAccessToken)
			throws DataAccessException {
		try {
			Connection conn = getConnection();
			String query = "SELECT userID FROM useraccountandinfo WHERE userAccessToken = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userAccessToken);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next())
				return rs.getInt(1);
			else
				return null;

		} catch (SQLException e) {
			throw new DataAccessException(
					"Error in db fetching user access token", e);
		}
	}

	@Override
	public User getUserGivenAccessToken(String accessToken) throws DataAccessException {
		
		User user = null;
		try {
			Connection conn = getConnection();
			String query = "SELECT * FROM useraccountandinfo WHERE userAccessToken = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, accessToken);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				user = new User(
						rs.getInt("userID"),
						rs.getString("username"),
						rs.getString("password"),
						(rs.getString("firstName") + " " + rs.getString("middleName") + " " + rs.getString("lastName")),
						rs.getString("birthdate"),
						rs.getString("gender"),
						rs.getDouble("heightInInches"),
						weightDao.getLatestWeight(accessToken).getWeightInPounds(),
						rs.getString("contactNumber"),
						rs.getString("emailAddress"),
						rs.getString(""),
						rs.getString(""),
						rs.getString(""),
						rs.getString(""));
			}
			
			return user;

		} catch (SQLException e) {
			throw new DataAccessException(
					"Error in db fetching user access token", e);
		}
	}

	@Override
	public void edit(User user) {
		// TODO Auto-generated method stub

	}
}
