package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import phr.dao.WeightDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.web.models.FBPost;
import phr.web.models.Weight;

@Repository("weightDao")
public class WeightDaoImpl extends BaseDaoSqlImpl implements WeightDao {

	@Override
	public void add(Weight weight) throws DataAccessException {
		
		try {
			Connection conn = getConnection();
			String query = "INSERT INTO weighttracker(weightInPounds, dateAdded, status, userID, fbPostID, photo) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setDouble(1, weight.getWeightInPounds());
			pstmt.setTimestamp(2, weight.getTimestamp());
			pstmt.setString(3, weight.getStatus());
			pstmt.setInt(4, weight.getUserID());
			if (weight.getFbPost() != null)
				pstmt.setInt(5, weight.getFbPost().getId());
			else
				pstmt.setInt(5, -1);
			pstmt.setString(6, weight.getImageFilePath());

			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

	}

	@Override
	public void edit(Weight weight) throws DataAccessException, EntryNotFoundException {
		try{
			Connection conn = getConnection();
			String query = "UPDATE weighttracker SET weightInPounds = ?, dateAdded = ?, status=?, photo=?" +
							"WHERE id = ?";
			
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setDouble(1, weight.getWeightInPounds());
			pstmt.setTimestamp(2, weight.getTimestamp());
			pstmt.setString(3, weight.getStatus());
			pstmt.setString(4, weight.getImageFilePath());
			pstmt.setInt(5, weight.getEntryID());

			pstmt.executeUpdate();
			
		}catch (Exception e){
			throw new EntryNotFoundException("Object ID not found in the database", e);
		}

	}

	@Override
	public void delete(Weight object) throws DataAccessException, EntryNotFoundException {
		try{
			Connection conn = getConnection();
			String query = "DELETE FROM weighttracker WHERE id = ?";
			
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, object.getEntryID());
			
			pstmt.executeUpdate();
			
		}catch (Exception e){
			throw new EntryNotFoundException("Object ID not found in the database", e);
		}
	}

	@Override
	public Weight get(int entryID) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Integer getUserID(String userAccessToken)
			throws DataAccessException, EntryNotFoundException {
		try{
			Connection conn = getConnection();
			String query = "SELECT id FROM useraccountandinfo WHERE userAccessToken = ?";
			
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userAccessToken);
			
			ResultSet rs = pstmt.executeQuery();

			if (rs.next())
				return rs.getInt(1);
			else
				return null;
			
		}catch (Exception e){
			throw new EntryNotFoundException("Object ID not found in the database", e);
		}
	}

	@Override
	public ArrayList<Weight> getAll(String userAccessToken) throws DataAccessException {
		
		 ArrayList<Weight> weights = new ArrayList<Weight>();
			try{
				Connection conn = getConnection();
				String query = "SELECT fbPostID, weightInPounds, status, photo, dateAdded FROM weighttracker WHERE userID = ?";
				
				PreparedStatement pstmt;
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, getUserID(userAccessToken));
				
				ResultSet rs = pstmt.executeQuery();
				 while (rs.next()) {
					 weights.add(new Weight(
							 new FBPost(rs.getInt("fbPostID")),
							 rs.getTimestamp("dateAdded"), 
							 rs.getString("status"), 
							 rs.getString("photo"),
							 rs.getDouble("weightInPounds")
							 ));
				 }
			}catch (Exception e){
				throw new DataAccessException("An error has occured while trying to access data from the database",
						e);
			}
		return weights;
	}

	@Override
	public Integer getEntryId(Weight weight) throws DataAccessException {
		
		try {
			Connection conn = getConnection();
			String query = "SELECT id FROM weighttracker WHERE "
					+ "userID = ? AND dateAdded = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, weight.getUserID());
			pstmt.setTimestamp(2, weight.getTimestamp());

			ResultSet rs = pstmt.executeQuery();

			if (rs.next())
				return rs.getInt(1);
			else
				return null;
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
	}


}
