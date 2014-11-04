package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import phr.dao.SportEstablishmentDao;
import phr.exceptions.DataAccessException;
import phr.models.SportEstablishment;

public class SportEstablishmentDaoSqlImpl extends BaseDaoSqlImpl implements SportEstablishmentDao {

	@Override
	public SportEstablishment getSportEstablishmentGivenGymName(String gymName) throws DataAccessException {
		
		SportEstablishment sportEstablishment = null;
		
		try{
			Connection conn = getConnection();
			String query = "SELECT * FROM gymlist WHERE name = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, gymName);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				sportEstablishment = new SportEstablishment(rs.getInt("id"), rs.getString("name"));
			}
			conn.close();
		}catch(Exception e){
			throw new DataAccessException("An error has occured while trying to access data from the database", e);
		}
		return sportEstablishment;
	}

	@Override
	public SportEstablishment getSportEstablishmentGivenGymID(int gymID) throws DataAccessException {
		SportEstablishment sportEstablishemt = new SportEstablishment(gymID);
		
		try{
			Connection conn = getConnection();
			String query = "SELECT * FROM gymlist WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, gymID);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				sportEstablishemt.setName(rs.getString("name"));
			}
			conn.close();
		}catch(Exception e){
			throw new DataAccessException("An error has occured while trying to access data from the database", e);
		}
		
		return sportEstablishemt;	
	}


}
