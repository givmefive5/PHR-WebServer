package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import phr.dao.UserDao;
import phr.dao.WeightDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.tools.ImageHandler;
import phr.web.models.FBPost;
import phr.web.models.PHRImage;
import phr.web.models.PHRImageType;
import phr.web.models.Weight;

@Repository("weightDao")
public class WeightDaoImpl extends BaseDaoSqlImpl implements WeightDao {

	@Autowired
	UserDao userDao;

	@Override
	public int addReturnsEntryID(Weight weight) throws DataAccessException {

		try {
			Connection conn = getConnection();
			String query = "INSERT INTO weighttracker(weightInPounds, dateAdded, status, userID, fbPostID, photo) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setDouble(1, weight.getWeightInPounds());
			pstmt.setTimestamp(2, weight.getTimestamp());
			pstmt.setString(3, weight.getStatus());
			pstmt.setInt(4, weight.getUserID());
			if (weight.getFbPost() != null)
				pstmt.setInt(5, weight.getFbPost().getId());
			else
				pstmt.setNull(5, Types.NULL);

			if (weight.getImage().getFileName() == null) {
				String encodedImage = weight.getImage().getEncodedImage();
				String fileName = ImageHandler
						.saveImage_ReturnFilePath(encodedImage);
				weight.getImage().setFileName(fileName);
			}

			pstmt.setString(6, weight.getImage().getFileName());

			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();

			int entryID = -1;
			if (rs.next())
				entryID = rs.getInt(1);

			return entryID;

		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

	}

	@Override
	public void edit(Weight weight) throws DataAccessException,
			EntryNotFoundException {
		try {
			Connection conn = getConnection();
			String query = "UPDATE weighttracker SET weightInPounds = ?, dateAdded = ?, status=?, photo=?"
					+ "WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setDouble(1, weight.getWeightInPounds());
			pstmt.setTimestamp(2, weight.getTimestamp());
			pstmt.setString(3, weight.getStatus());
			pstmt.setString(4, weight.getImage().getFileName());
			pstmt.setInt(5, weight.getEntryID());

			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new EntryNotFoundException(
					"Object ID not found in the database", e);
		}

	}

	@Override
	public void delete(Weight object) throws DataAccessException,
			EntryNotFoundException {
		try {
			Connection conn = getConnection();
			String query = "DELETE FROM weighttracker WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, object.getEntryID());

			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new EntryNotFoundException(
					"Object ID not found in the database", e);
		}
	}

	@Override
	public ArrayList<Weight> getAll(String userAccessToken)
			throws DataAccessException {

		ArrayList<Weight> weights = new ArrayList<Weight>();
		try {
			Connection conn = getConnection();
			String query = "SELECT id, fbPostID, weightInPounds, status, photo, dateAdded FROM weighttracker WHERE userID = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, userDao.getUserIDGivenAccessToken(userAccessToken));

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				PHRImage image = new PHRImage(rs.getString("photo"),
						PHRImageType.FILENAME);
				weights.add(new Weight(rs.getInt("id"), new FBPost(rs
						.getInt("fbPostID")), rs.getTimestamp("dateAdded"), rs
						.getString("status"), image, rs
						.getDouble("weightInPounds")));
			}
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
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
