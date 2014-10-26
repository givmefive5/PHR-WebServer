package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import phr.dao.SportEstablishmentDao;
import phr.exceptions.DataAccessException;
import phr.models.Activity;
import phr.models.Food;

public class SportEstablishmentDaoSqlImpl extends BaseDaoSqlImpl implements SportEstablishmentDao {

	@Override
	public List<Activity> getActivity(int gymID) throws DataAccessException {
		
		List<Activity> activities = new ArrayList<Activity>();

		try {
			Connection conn = getConnection();
			String query = "";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, gymID);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				
			}
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
		return activities;
	}

	@Override
	public Integer getGymID(String gymName) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

}
