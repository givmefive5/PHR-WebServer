package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import phr.dao.NotesDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.web.models.FBPost;
import phr.web.models.Note;
import phr.web.models.PHRImage;
import phr.web.models.PHRImageType;

public class NotesDaoSqlImpl extends BaseDaoSqlImpl implements NotesDao {

	@Autowired
	UserDao userDao;

	@Override
	public void add(Note note) throws DataAccessException {
		try {
			Connection conn = getConnection();
			String query = "INSERT INTO notestracker (title, note, dateAdded, status, userID, fbPostID, photo) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, note.getTitle());
			pstmt.setString(2, note.getNote());
			pstmt.setTimestamp(3, note.getTimestamp());
			pstmt.setString(4, note.getStatus());
			pstmt.setInt(5, note.getUserID());
			if (note.getFbPost() != null)
				pstmt.setInt(6, note.getFbPost().getId());
			else
				pstmt.setInt(6, -1);
			pstmt.setString(7, note.getImage().getFileName());

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
			String query = "UPDATE notestracker SET title = ?, note = ?, dateAdded = ?, status=?, photo=?"
					+ "WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, note.getTitle());
			pstmt.setString(2, note.getNote());
			pstmt.setTimestamp(3, note.getTimestamp());
			pstmt.setString(4, note.getStatus());
			pstmt.setString(5, note.getImage().getFileName());
			pstmt.setInt(6, note.getEntryID());

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
			String query = "SELECT fbPostID, title, note, status, photo, dateAdded FROM bloodpressuretracker WHERE userID = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, userDao.getUserIDGivenAccessToken(userAccessToken));

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				PHRImage image = new PHRImage(rs.getString("photo"),
						PHRImageType.FILENAME);
				notes.add(new Note(new FBPost(rs.getInt("fbPostID")), rs
						.getTimestamp("dateAdded"), rs.getString("status"),
						image, rs.getString("title"), rs.getString("note")));
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
