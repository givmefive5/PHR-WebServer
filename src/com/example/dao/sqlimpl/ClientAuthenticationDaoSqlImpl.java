package com.example.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.example.dao.ClientAuthenticationDao;
import com.example.exceptions.DataAccessException;

@Repository("clientAuthenticationDao")
public class ClientAuthenticationDaoSqlImpl extends BaseDaoSqlImpl implements
		ClientAuthenticationDao {

	@Override
	public boolean isAuthorizedClient(String clientID, String clientPassword)
			throws DataAccessException {

		int count = 0;

		try {
			Connection conn = getConnection();
			String query = "SELECT COUNT(*) FROM client "
					+ "WHERE clientid = ? AND clientpassword = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, clientID);
			pstmt.setString(2, clientPassword);

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

		if (count == 1)
			return true;
		else
			return false;
	}

}
