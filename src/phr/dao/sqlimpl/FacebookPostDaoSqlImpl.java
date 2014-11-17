package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import phr.dao.FacebookPostDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;

public class FacebookPostDaoSqlImpl extends BaseDaoSqlImpl implements
		FacebookPostDao {

	UserDao userDao = new UserDaoSqlImpl();

	@Override
	public List<String> getAllFacebookID(String accessToken)
			throws DataAccessException {

		List<String> facebookIDList = new ArrayList<String>();

		try {
			Connection conn = getConnection();
			String query = "SELECT facebookID FROM activitytracker WHERE userID = ? UNION "
					+ "SELECT facebookID FROM bloodpressuretracker WHERE userID = ? UNION "
					+ "SELECT facebookID FROM bloodsugartracker WHERE userID = ? UNION "
					+ "SELECT facebookID FROM checkuptracker WHERE userID = ? UNION "
					+ "SELECT facebookID FROM foodtracker WHERE userID = ? UNION "
					+ "SELECT facebookID FROM notestracker WHERE userID = ? UNION "
					+ "SELECT facebookID FROM tempactivitytracker WHERE userID =? UNION "
					+ "SELECT facebookID FROM tempfoodtracker WHERE userID = ? UNION "
					+ "SELECT facebookID FROM temprestaurant WHERE userID = ? UNION "
					+ "SELECT facebookID FROM tempsportestablishment WHERE userID = ? UNION "
					+ "SELECT facebookID FROM weighttracker "
					+ "WHERE userID = ?";
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			Integer id = userDao.getUserIDGivenAccessToken(accessToken);
			for (int i = 1; i <= 11; i++)
				pstmt.setInt(i, id);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				facebookIDList.add(rs.getString("facebookID"));
			}
			conn.close();
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

		return facebookIDList;
	}

	@Override
	public Timestamp getLatestTimestamp(String accessToken)
			throws DataAccessException {
		Timestamp latestTimestamp = null;

		try {
			Connection conn = getConnection();
			String query = "SELECT dateAdded FROM activitytracker WHERE userID = ? AND facebookID IS NOT NULL UNION  "
					+ "SELECT dateAdded FROM bloodpressuretracker WHERE userID = ? AND facebookID IS NOT NULL UNION "
					+ "SELECT dateAdded FROM bloodsugartracker WHERE userID = ? AND facebookID IS NOT NULL UNION "
					+ "SELECT dateAdded FROM checkuptracker WHERE userID = ? AND facebookID IS NOT NULL UNION "
					+ "SELECT dateAdded FROM foodtracker WHERE userID = ? AND facebookID IS NOT NULL UNION "
					+ "SELECT dateAdded FROM notestracker WHERE userID = ? AND facebookID IS NOT NULL UNION "
					+ "SELECT dateAdded FROM tempactivitytracker WHERE userID = ? AND facebookID IS NOT NULL UNION "
					+ "SELECT dateAdded FROM tempfoodtracker WHERE userID = ? AND facebookID IS NOT NULL UNION "
					+ "SELECT dateAdded FROM temprestaurant WHERE userID = ? AND facebookID IS NOT NULL UNION "
					+ "SELECT dateAdded FROM weighttracker "
					+ "WHERE userID = ? AND facebookID IS NOT NULL "
					+ "ORDER BY dateAdded DESC LIMIT 1";
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			Integer id = userDao.getUserIDGivenAccessToken(accessToken);
			for (int i = 1; i <= 10; i++)
				pstmt.setInt(i, id);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				latestTimestamp = rs.getTimestamp("dateAdded");
			}
			System.out.println("Latest Timestamp from DB" + latestTimestamp);
			if (latestTimestamp == null) {
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date date = dateFormat.parse("03/11/2014");
				long time = date.getTime();
				Timestamp startDate = new Timestamp(time);
				latestTimestamp = new Timestamp(startDate.getTime());
			}
			conn.close();
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
		return latestTimestamp;
	}

	@Override
	public List<String> getAllDeletedFacebokID(String AccessToken)
			throws DataAccessException {

		List<String> facebookIDList = new ArrayList<String>();

		try {
			Connection conn = getConnection();
			String query = "SELECT facebookID FROM deletedfbpost "
					+ "WHERE userID = ?";
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, userDao.getUserIDGivenAccessToken(AccessToken));

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				facebookIDList.add(rs.getString("facebookID"));
			}
			conn.close();
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

		return facebookIDList;
	}

	@Override
	public void addDeletedFacebookID(int UserID, String FacebookID)
			throws DataAccessException {

		try {
			Connection conn = getConnection();
			String query = "INSERT INTO deletedfbpost(facebookID, userID) VALUES (?, ?) ";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, FacebookID);
			pstmt.setInt(2, UserID);

			pstmt.executeUpdate();

			conn.close();
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

	}

}
