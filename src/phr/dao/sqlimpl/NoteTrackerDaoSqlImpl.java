package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import phr.dao.NoteTrackerDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.models.FBPost;
import phr.models.Note;
import phr.models.PHRImage;
import phr.models.PHRImageType;
import phr.tools.ImageHandler;

@Repository("noteTrackerDao")
public class NoteTrackerDaoSqlImpl extends BaseDaoSqlImpl implements NoteTrackerDao {

	@Autowired
	UserDao userDao;

	@Override
	public int addReturnsEntryID(Note note) throws DataAccessException {
		try {
			Connection conn = getConnection();
			String query = "INSERT INTO notestracker (note, dateAdded, status, userID, facebookID, photo) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, note.getNote());
			pstmt.setTimestamp(2, note.getTimestamp());
			pstmt.setString(3, note.getStatus());
			pstmt.setInt(4, note.getUserID());
			if (note.getFacebookID() != null)
				pstmt.setString(5, note.getFacebookID());
			else
				pstmt.setNull(5, Types.NULL);
			
			if(note.getImage()!= null){
				String encodedImage = note.getImage().getEncodedImage();
				String fileName = ImageHandler
						.saveImage_ReturnFilePath(encodedImage);
				note.getImage().setFileName(fileName);
				pstmt.setString(6, note.getImage().getFileName());
			}
			else
				pstmt.setNull(6, Types.NULL);


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
	public void edit(Note note) throws DataAccessException,
			EntryNotFoundException {
		try {
			Connection conn = getConnection();
			String query = "UPDATE notestracker SET note = ?, dateAdded = ?, status=?, photo=? "
					+ "WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, note.getNote());
			pstmt.setTimestamp(2, note.getTimestamp());
			pstmt.setString(3, note.getStatus());
			if(note.getImage()!= null)
			{
				String encodedImage = note.getImage().getEncodedImage();
				String fileName = ImageHandler
						.saveImage_ReturnFilePath(encodedImage);
				note.getImage().setFileName(fileName);
				pstmt.setString(4, note.getImage().getFileName());
			}
			else
				pstmt.setNull(4, Types.NULL);
			pstmt.setInt(5, note.getEntryID());

			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new EntryNotFoundException(
					"Object ID not found in the database", e);
		}
	}

	@Override
	public void delete(Note note) throws DataAccessException,
			EntryNotFoundException {
		try {
			Connection conn = getConnection();
			String query = "DELETE FROM notestracker WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, note.getEntryID());

			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new EntryNotFoundException(
					"Object ID not found in the database", e);
		}

	}

	@Override
	public List<Note> getAll(String userAccessToken)
			throws DataAccessException {
		List<Note> notes = new ArrayList<Note>();
		try {
			Connection conn = getConnection();
			String query = "SELECT id, facebookID, note, status, photo, dateAdded FROM bloodpressuretracker WHERE userID = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, userDao.getUserIDGivenAccessToken(userAccessToken));

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				PHRImage image = null;
				if(rs.getString("photo") == null)
					image = null;
				else{
					String encodedImage = ImageHandler.getEncodedImageFromFile(rs.getString("photo"));
					image = new PHRImage(encodedImage, PHRImageType.IMAGE);
				}
				notes.add(new Note(rs.getInt("id"), rs.getString("facebookID"), rs.getTimestamp("dateAdded"), rs
						.getString("status"), image, rs.getString("note")));
			}
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
		return notes;
	}

	@Override
	public Integer getEntryId(Note note) throws DataAccessException {
		try {
			Connection conn = getConnection();
			String query = "SELECT id FROM notestracker WHERE "
					+ "userID = ? AND dateAdded = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, note.getUserID());
			pstmt.setTimestamp(2, note.getTimestamp());

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
