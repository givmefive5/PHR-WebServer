package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import phr.dao.BloodSugarDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.tools.ImageHandler;
import phr.web.models.BloodSugar;
import phr.web.models.FBPost;
import phr.web.models.PHRImage;
import phr.web.models.PHRImageType;

@Repository("bloodSugar")
public class BloodSugarDaoImpl extends BaseDaoSqlImpl implements BloodSugarDao {

	@Autowired
	UserDao userDao;

	@Override
	public int addReturnsEntryID(BloodSugar bloodSugar)
			throws DataAccessException {
		try {
			Connection conn = getConnection();
			String query = "INSERT INTO bloodsugartracker (bloodsugar, type,  dateAdded, status, userID, fbPostID, photo) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setDouble(1, bloodSugar.getBloodSugar());
			pstmt.setString(2, bloodSugar.getType());
			pstmt.setTimestamp(3, bloodSugar.getTimestamp());
			pstmt.setString(4, bloodSugar.getStatus());
			pstmt.setInt(5, bloodSugar.getUserID());
			if (bloodSugar.getFbPost() != null)
				pstmt.setInt(6, bloodSugar.getFbPost().getId());
			else
				pstmt.setNull(6, Types.NULL);

			if (bloodSugar.getImage().getFileName() == null) {
				String encodedImage = bloodSugar.getImage().getEncodedImage();
				String fileName = ImageHandler
						.saveImage_ReturnFilePath(encodedImage);
				bloodSugar.getImage().setFileName(fileName);
			}

			pstmt.setString(7, bloodSugar.getImage().getFileName());

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
	public void edit(BloodSugar bloodSugar) throws DataAccessException,
			EntryNotFoundException {
		try {
			Connection conn = getConnection();
			String query = "UPDATE bloodsugartracker SET bloodsugar = ?, type = ?,  dateAdded = ?, status=?, photo=?"
					+ "WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setDouble(1, bloodSugar.getBloodSugar());
			pstmt.setString(2, bloodSugar.getType());
			pstmt.setTimestamp(3, bloodSugar.getTimestamp());
			pstmt.setString(4, bloodSugar.getStatus());
			pstmt.setString(5, bloodSugar.getImage().getFileName());
			pstmt.setInt(6, bloodSugar.getEntryID());

			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new EntryNotFoundException(
					"Object ID not found in the database", e);
		}

	}

	@Override
	public void delete(BloodSugar bloodSugar) throws DataAccessException,
			EntryNotFoundException {
		try {
			Connection conn = getConnection();
			String query = "DELETE FROM bloodsugartracker WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bloodSugar.getEntryID());

			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new EntryNotFoundException(
					"Object ID not found in the database", e);
		}
	}

	@Override
	public ArrayList<BloodSugar> getAll(String userAccessToken)
			throws DataAccessException {
		ArrayList<BloodSugar> bloodsugars = new ArrayList<BloodSugar>();
		try {
			Connection conn = getConnection();
			String query = "SELECT id, fbPostID, bloodsugar, type,  status, photo, dateAdded FROM bloodsugartracker WHERE userID = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, userDao.getUserIDGivenAccessToken(userAccessToken));

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				PHRImage image = new PHRImage(rs.getString("photo"),
						PHRImageType.FILENAME);
				if(image.getFileName()!= null){
					String encodedImage = ImageHandler.getEncodedImageFromFile(image.getFileName());
					image.setEncodedImage(encodedImage);
					image.setFileName(null);
				}
				bloodsugars.add(new BloodSugar(rs.getInt("id"), new FBPost(rs
						.getInt("fbPostID")), rs.getTimestamp("dateAdded"), rs
						.getString("status"), image,
						rs.getDouble("bloodsugar"), rs.getString("type")));
			}
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

		return bloodsugars;
	}

	@Override
	public Integer getEntryId(BloodSugar bloodSugar) throws DataAccessException {
		try {
			Connection conn = getConnection();
			String query = "SELECT id FROM bloodsugartracker WHERE "
					+ "userID = ? AND dateAdded = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bloodSugar.getUserID());
			pstmt.setTimestamp(2, bloodSugar.getTimestamp());

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
