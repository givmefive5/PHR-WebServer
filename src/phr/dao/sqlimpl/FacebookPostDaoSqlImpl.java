package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import phr.dao.FacebookPostDao;
import phr.exceptions.DataAccessException;

public class FacebookPostDaoSqlImpl extends BaseDaoSqlImpl implements FacebookPostDao {

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
					+ "SELECT facebookID FROM weighttracker";
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				facebookIDList.add(rs.getString("facebookID"));
			}
			
		}catch(Exception e){
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
		
		return facebookIDList;
	}


}
