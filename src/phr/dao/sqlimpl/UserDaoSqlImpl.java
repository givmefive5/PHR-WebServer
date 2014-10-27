package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.stereotype.Repository;

import phr.dao.UserDao;
import phr.dao.WeightTrackerDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.UsernameAlreadyExistsException;
import phr.models.User;
import phr.tools.Hasher;
import phr.tools.ImageHandler;

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
				String query = "INSERT INTO useraccountandinfo(username, password, fullname, birthdate, gender, heightInInches, weight, "
						+ "contactNumber, emailAddress, emergencyPerson, emergencyContactNumber, allergies, knownHealthProblems, fbAccessToken, photo) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement pstmt;

				String hashedPassword = Hasher.hashString(user.getPassword());
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, user.getUsername());
				pstmt.setString(2, hashedPassword);
				pstmt.setString(3, user.getName());
				pstmt.setTimestamp(4, user.getDateOfBirth());
				pstmt.setString(5, user.getGender());
				pstmt.setDouble(6, user.getHeight());
				pstmt.setDouble(7, user.getWeight());
				pstmt.setString(8, user.getContactNumber());
				pstmt.setString(9, user.getEmail());
				pstmt.setString(10, user.getEmergencyPerson());
				pstmt.setString(11, user.getEmergencyContactNumber());
				
				if(user.getAllergies() != null)
					pstmt.setString(12, user.getAllergies());
				else
					pstmt.setNull(12, Types.NULL);
				
				if(user.getKnownHealthProblems() != null)
					pstmt.setString(13, user.getKnownHealthProblems());
				else
					pstmt.setNull(13, Types.NULL);
				
				if(user.getFbAccessToken() != null)
					pstmt.setString(14, user.getFbAccessToken());
				else
					pstmt.setNull(14, Types.NULL);
				
				if (user.getPhoto() != null) {
					String encodedImage = user.getPhoto().getEncodedImage();
					String fileName = ImageHandler.saveImage_ReturnFilePath(encodedImage);
					user.getPhoto().setFileName(fileName);
					pstmt.setString(15, user.getPhoto().getFileName());
				}
				else
					pstmt.setNull(15, Types.NULL);

				pstmt.executeUpdate();

			} catch (Exception e) {
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
						rs.getString("username"),
						rs.getString("password"));
			}
			
			return user;

		} catch (SQLException e) {
			throw new DataAccessException(
					"Error in db fetching user access token", e);
		}
	}

	@Override
	public void edit(User user) throws DataAccessException {
	
		try {
			Connection conn = getConnection();
			String query = "UPDATE useraccountandinfo SET username = ?, password = ?, fullname = ?, birthdate = ?, gender = ?, heightInInches = ?, weight =?, "
					+ "contactNumber = ?, emailAddress = ?, emergencyPerson = ?, emergencyContactNumber = ?, allergies = ?, knownHealthProblems = ?, fbAccessToken = ?, photo = ?) "
					+ "WHERE userID = ?";
			
			PreparedStatement pstmt;

			String hashedPassword = Hasher.hashString(user.getPassword());
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, hashedPassword);
			pstmt.setString(3, user.getName());
			pstmt.setTimestamp(4, user.getDateOfBirth());
			pstmt.setString(5, user.getGender());
			pstmt.setDouble(6, user.getHeight());
			pstmt.setDouble(7, user.getWeight());
			pstmt.setString(8, user.getContactNumber());
			pstmt.setString(9, user.getEmail());
			pstmt.setString(10, user.getEmergencyPerson());
			pstmt.setString(11, user.getEmergencyContactNumber());
			
			if(user.getAllergies() != null)
				pstmt.setString(12, user.getAllergies());
			else
				pstmt.setNull(12, Types.NULL);
			
			if(user.getKnownHealthProblems() != null)
				pstmt.setString(13, user.getKnownHealthProblems());
			else
				pstmt.setNull(13, Types.NULL);
			
			if(user.getFbAccessToken() != null)
				pstmt.setString(14, user.getFbAccessToken());
			else
				pstmt.setNull(14, Types.NULL);
			
			if (user.getPhoto() != null) {
				String encodedImage = user.getPhoto().getEncodedImage();
				String fileName = ImageHandler.saveImage_ReturnFilePath(encodedImage);
				user.getPhoto().setFileName(fileName);
				pstmt.setString(15, user.getPhoto().getFileName());
			}
			else
				pstmt.setNull(15, Types.NULL);

			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

	}
}
