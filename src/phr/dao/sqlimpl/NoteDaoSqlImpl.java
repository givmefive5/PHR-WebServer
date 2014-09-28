package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import phr.dao.NoteDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.web.models.FBPost;
import phr.web.models.Note;
import phr.web.models.PHRImage;
import phr.web.models.PHRImageType;

@Repository("note")
public class NoteDaoSqlImpl extends BaseDaoSqlImpl implements NoteDao {

	@Autowired
	UserDao userDao;

	@Override
	public void add(Note note) throws DataAccessException {
		try {
			Connection conn = getConnection();
			String query = "INSERT INTO notestracker (note, dateAdded, status, userID, fbPostID, photo) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, note.getNote());
			pstmt.setTimestamp(2, note.getTimestamp());
			pstmt.setString(3, note.getStatus());
			pstmt.setInt(4, note.getUserID());
			if (note.getFbPost() != null)
				pstmt.setInt(5, note.getFbPost().getId());
			else
				pstmt.setInt(5, -1);
			pstmt.setString(6, note.getImage().getFileName());

			pstmt.executeUpdate();
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
			String query = "UPDATE notestracker SET note = ?, dateAdded = ?, status=?, photo=?"
					+ "WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, note.getNote());
			pstmt.setTimestamp(2, note.getTimestamp());
			pstmt.setString(3, note.getStatus());
			pstmt.setString(4, note.getImage().getFileName());
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
	public ArrayList<Note> getAll(String userAccessToken)
			throws DataAccessException {
		ArrayList<Note> notes = new ArrayList<Note>();
		try {
			Connection conn = getConnection();
			String query = "SELECT id, fbPostID, note, status, photo, dateAdded FROM bloodpressuretracker WHERE userID = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, userDao.getUserIDGivenAccessToken(userAccessToken));

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				PHRImage image = new PHRImage(rs.getString("photo"),PHRImageType.FILENAME);
				notes.add(new Note(
						rs.getInt("id"),
						new FBPost(rs.getInt("fbPostID")),
						rs.getTimestamp("dateAdded"), 
						rs.getString("status"),
						image,
						rs.getString("note")));
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
