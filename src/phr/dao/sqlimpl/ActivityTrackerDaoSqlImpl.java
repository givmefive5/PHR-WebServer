package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import phr.dao.ActivityDao;
import phr.dao.ActivityTrackerDao;
import phr.dao.FacebookPostDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.models.ActivityTrackerEntry;
import phr.models.PHRImage;
import phr.models.PHRImageType;
import phr.tools.ImageHandler;

@Repository("activityTrackerDao")
public class ActivityTrackerDaoSqlImpl extends BaseDaoSqlImpl implements
		ActivityTrackerDao {

	// @Autowired
	// UserDao userDao;

	// @Autowired
	// ActivityDao activityDao;

	UserDao userDao = new UserDaoSqlImpl();
	ActivityDao activityDao = new ActivityDaoSqlImpl();
	FacebookPostDao facebookPostDao = new FacebookPostDaoSqlImpl();

	@Override
	public int addReturnsEntryID(ActivityTrackerEntry activityTrackerEntry)
			throws DataAccessException {

		try {
			Connection conn = getConnection();
			String query = "INSERT INTO activitytracker(activityID, durationInSeconds, calorieBurnedPerHour, dateAdded, status, userID, facebookID, photo) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, activityTrackerEntry.getActivity().getEntryID());
			pstmt.setInt(2, activityTrackerEntry.getDurationInSeconds());
			System.out.println(activityTrackerEntry.getCaloriesBurnedPerHour());
			pstmt.setDouble(3, activityTrackerEntry.getCaloriesBurnedPerHour());
			pstmt.setTimestamp(4, activityTrackerEntry.getTimestamp());
			pstmt.setString(5, activityTrackerEntry.getStatus());
			pstmt.setInt(6, activityTrackerEntry.getUserID());

			if (activityTrackerEntry.getFacebookID() != null)
				pstmt.setString(7, activityTrackerEntry.getFacebookID());
			else
				pstmt.setNull(7, Types.NULL);

			if (activityTrackerEntry.getImage() != null) {
				String encodedImage = activityTrackerEntry.getImage()
						.getEncodedImage();
				String fileName = ImageHandler
						.saveImage_ReturnFilePath(encodedImage);
				activityTrackerEntry.getImage().setFileName(fileName);
				pstmt.setString(8, activityTrackerEntry.getImage()
						.getFileName());
			} else
				pstmt.setNull(8, Types.NULL);

			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();

			int entryID = -1;
			if (rs.next())
				entryID = rs.getInt(1);
			conn.close();
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
			String query = "UPDATE activitytracker SET activityID = ?, durationInSeconds = ?,  calorieBurnedPerHour = ?, dateAdded =? , status = ?, photo = ? "
					+ "WHERE id = ?";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, activityTrackerEntry.getActivity().getEntryID());
			pstmt.setInt(2, activityTrackerEntry.getDurationInSeconds());
			pstmt.setDouble(3, activityTrackerEntry.getCaloriesBurnedPerHour());
			pstmt.setTimestamp(4, activityTrackerEntry.getTimestamp());
			pstmt.setString(5, activityTrackerEntry.getStatus());
			if (activityTrackerEntry.getImage() != null) {
				String encodedImage = activityTrackerEntry.getImage()
						.getEncodedImage();
				String fileName = ImageHandler
						.saveImage_ReturnFilePath(encodedImage);
				activityTrackerEntry.getImage().setFileName(fileName);
				pstmt.setString(6, activityTrackerEntry.getImage()
						.getFileName());
			} else
				pstmt.setNull(6, Types.NULL);

			pstmt.setInt(7, activityTrackerEntry.getEntryID());

			pstmt.executeUpdate();
			conn.close();
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

			if (activityTrackerEntry.getFacebookID() != null)
				facebookPostDao.addDeletedFacebookID(
						activityTrackerEntry.getUserID(),
						activityTrackerEntry.getFacebookID());

			Connection conn = getConnection();
			String query = "DELETE FROM activitytracker WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, activityTrackerEntry.getEntryID());

			pstmt.executeUpdate();

			conn.close();
		} catch (Exception e) {
			throw new EntryNotFoundException(
					"Object ID not found in the database", e);
		}

	}

	@Override
	public List<ActivityTrackerEntry> getAll(String userAccessToken)
			throws DataAccessException {

		List<ActivityTrackerEntry> activities = new ArrayList<ActivityTrackerEntry>();

		try {
			Connection conn = getConnection();
			String query = "SELECT id, activityID, durationInSeconds, calorieBurnedPerHour, facebookID, status, photo, dateAdded "
					+ "FROM activityTracker WHERE userID = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, userDao.getUserIDGivenAccessToken(userAccessToken));

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				PHRImage image = null;
				if (rs.getString("photo") == null)
					image = null;
				else {
					String encodedImage = ImageHandler
							.getEncodedImageFromFile(rs.getString("photo"));
					image = new PHRImage(encodedImage, PHRImageType.IMAGE);
				}

				activities.add(new ActivityTrackerEntry(rs.getInt("id"), rs
						.getString("facebookID"), rs.getTimestamp("dateAdded"),
						rs.getString("status"), image, activityDao
								.getActivity(rs.getInt("activityID")), rs
								.getDouble("calorieBurnedPerHour"), rs
								.getInt("durationInSeconds")));
			}
			conn.close();
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

		return activities;

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

			Integer id = null;
			if (rs.next())
				id = rs.getInt(1);

			conn.close();
			return id;
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
	}

}
