package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import phr.dao.FacebookPostDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;

public class FacebookPostDaoSqlImpl extends BaseDaoSqlImpl implements FacebookPostDao {
	
	UserDao userDao = new UserDaoSqlImpl();

	@Override
	public List<String> getAllFacebookID(String accessToken) throws DataAccessException {

		List<String> facebookIDList = new ArrayList<String>();
		
		try{
			Connection conn = getConnection();
			String query = "SELECT facebookID FROM activitytracker UNION "
					+ "SELECT facebookID FROM bloodpressuretracker UNION "
					+ "SELECT facebookID FROM bloodsugartracker UNION "
					+ "SELECT facebookID FROM checkuptracker UNION "
					+ "SELECT facebookID FROM foodtracker UNION "
					+ "SELECT facebookID FROM notestracker UNION "
					+ "SELECT facebookID FROM tempactivitytracker UNION "
					+ "SELECT facebookID FROM tempfoodtracker UNION "
					+ "SELECT facebookID FROM temprestaurant UNION "
					+ "SELECT facebookID FROM weighttracker "
					+ "WHERE userID = ?";
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, userDao.getUserIDGivenAccessToken(accessToken));
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				facebookIDList.add(rs.getString("facebookID"));
			}
			conn.close();
		}catch(Exception e){
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
		
		return facebookIDList;
	}

	@Override
	public Timestamp getLatestTimestamp(String accessToken) throws DataAccessException {
		Timestamp latestTimestamp = null;
		
		try{
			Connection conn = getConnection();
			String query = "SELECT dateAdded FROM activitytracker UNION "
					+ "SELECT dateAdded FROM bloodpressuretracker UNION "
					+ "SELECT dateAdded FROM bloodsugartracker UNION "
					+ "SELECT dateAdded FROM checkuptracker UNION "
					+ "SELECT dateAdded FROM foodtracker UNION "
					+ "SELECT dateAdded FROM notestracker UNION "
					+ "SELECT dateAdded FROM tempactivitytracker UNION "
					+ "SELECT dateAdded FROM tempfoodtracker UNION "
					+ "SELECT dateAdded FROM temprestaurant UNION "
					+ "SELECT dateAdded FROM weighttracker "
					+ "WHERE userID = ? "
					+ "ORDER BY dateAdded DESC LIMIT 1";
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, userDao.getUserIDGivenAccessToken(accessToken));
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				latestTimestamp =  rs.getTimestamp("dateAdded");
			}
			conn.close();
		}catch(Exception e){
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
		return latestTimestamp;
	}


}
