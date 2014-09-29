package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import phr.dao.ActivityDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.tools.ImageHandler;
import phr.web.models.Activity;
import phr.web.models.ActivityTrackerEntry;

@Repository("activityDao")
public class ActivityDaoSqlImpl extends BaseDaoSqlImpl implements ActivityDao {

	@Autowired
	UserDao userDao;

	@Override
	public int addReturnsEntryID(ActivityTrackerEntry activityTrackerEntry)
			throws DataAccessException {

		try {
			Connection conn = getConnection();
			String query = "INSERT INTO activitytracker(activityID, calorieBurnedPerHour, dateAdded, status, userID, fbPostID, photo) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, activityTrackerEntry.getActivity().getEntryID());
			pstmt.setDouble(2, activityTrackerEntry.getCalorisBurnedPerHour());
			pstmt.setTimestamp(3, activityTrackerEntry.getTimestamp());
			pstmt.setString(4, activityTrackerEntry.getStatus());
			pstmt.setInt(5, activityTrackerEntry.getUserID());
			if (activityTrackerEntry.getFbPost() != null)
				pstmt.setInt(6, activityTrackerEntry.getFbPost().getId());
			else
				pstmt.setNull(6, Types.NULL);
			if (activityTrackerEntry.getImage().getFileName() == null) {
				String encodedImage = activityTrackerEntry.getImage()
						.getEncodedImage();
				String fileName = ImageHandler
						.saveImage_ReturnFilePath(encodedImage);
				activityTrackerEntry.getImage().setFileName(fileName);
			}
			pstmt.setString(7, activityTrackerEntry.getImage().getFileName());

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
	public void edit(ActivityTrackerEntry activityTrackerEntry)
			throws DataAccessException, EntryNotFoundException {

		try {
			Connection conn = getConnection();
			String query = "UPDATE activitytracker SET activityID = ?, calorieBurnedPerHour = ?, dateAdded =? , status = ?, photo = ?)"
					+ "WHERE id = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, activityTrackerEntry.getActivity().getEntryID());
			pstmt.setDouble(2, activityTrackerEntry.getCalorisBurnedPerHour());
			pstmt.setTimestamp(3, activityTrackerEntry.getTimestamp());
			pstmt.setString(4, activityTrackerEntry.getStatus());
			if (activityTrackerEntry.getImage().getFileName() == null) {
				String encodedImage = activityTrackerEntry.getImage()
						.getEncodedImage();
				String fileName = ImageHandler
						.saveImage_ReturnFilePath(encodedImage);
				activityTrackerEntry.getImage().setFileName(fileName);
			}
			pstmt.setString(5, activityTrackerEntry.getImage().getFileName());

			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

	}

	@Override
	public void delete(ActivityTrackerEntry activityTrackerEntry)
			throws DataAccessException, EntryNotFoundException {

		try {
			Connection conn = getConnection();
			String query = "DELETE FROM activitytracker WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, activityTrackerEntry.getEntryID());

			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new EntryNotFoundException(
					"Object ID not found in the database", e);
		}

	}

	@Override
	public ArrayList<ActivityTrackerEntry> getAll(String userAccessToken)
			throws DataAccessException {

		return null;

	}

	@Override
	public Integer getEntryId(ActivityTrackerEntry activityTrackerEntry)
			throws DataAccessException {

		try {
			Connection conn = getConnection();
			String query = "SELECT id FROM activitytracker WHERE "
					+ "userID = ? AND dateAdded = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, activityTrackerEntry.getUserID());
			pstmt.setTimestamp(2, activityTrackerEntry.getTimestamp());

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

	@Override
	public int addActivityListEntryReturnEntryID(Activity activity)
			throws DataAccessException {

		try {
			Connection conn = getConnection();
			String query = "INSERT INTO activitylist(name, MET) VALUES (?, ?)";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, activity.getName());
			pstmt.setDouble(2, activity.getMET());

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
	public ArrayList<Activity> getAllActivity() throws DataAccessException {

		ArrayList<Activity> activities = new ArrayList<Activity>();

		try {
			Connection conn = getConnection();
			String query = "SELECT id, name, MET FROM activityList";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				activities.add(new Activity(rs.getInt("id"), rs
						.getString("name"), rs.getDouble("MET")));
			}
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

		return activities;

	}
}
