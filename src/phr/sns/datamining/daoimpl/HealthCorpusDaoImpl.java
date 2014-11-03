package phr.sns.datamining.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import phr.dao.sqlimpl.BaseDaoSqlImpl;
import phr.exceptions.DataAccessException;
import phr.sns.datamining.dao.HealthCorpusDao;

@Repository("healthCorpusDao")
public class HealthCorpusDaoImpl extends BaseDaoSqlImpl implements
		HealthCorpusDao {

	@Override
	public List<String> getFoodWords() throws DataAccessException {
		List<String> foodList = new ArrayList<>();
		try {
			Connection conn = getConnection();
			String query = "SELECT name FROM foodlist";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				foodList.add(rs.getString("name"));
			}
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

		return foodList;
	}

	@Override
	public List<String> getActivityWords() throws DataAccessException {

		List<String> activityList = new ArrayList<String>();

		try {
			Connection conn = getConnection();
			String query = "SELECT wordTenses FROM activitycorpus";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				activityList.add(rs.getString("wordTenses"));
			}
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
		return activityList;
	}

	@Override
	public List<String> getRestaurantNames() throws DataAccessException {
		List<String> restaurantList = new ArrayList<>();
		try {
			Connection conn = getConnection();
			String query = "SELECT name FROM restaurantlist";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				restaurantList.add(rs.getString("name"));
			}
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

		return restaurantList;
	}

	@Override
	public List<String> getSportsEstablishmentNames()
			throws DataAccessException {

		List<String> sportEstablishmemtList = new ArrayList<>();
		try {
			Connection conn = getConnection();
			String query = "SELECT name FROM gymList";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				sportEstablishmemtList.add(rs.getString("name"));
			}
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

		return sportEstablishmemtList;
	}

}
