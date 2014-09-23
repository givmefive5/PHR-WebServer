package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
	public void add(ActivityTrackerEntry activityTrackerEntry) throws DataAccessException {
		
		try {
			Connection conn = getConnection();
			String query = "INSERT INTO activitytracker(activityID, caloriesBurnedPerHour, dateAdded, status, userID, fbPostID, photo) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, activityTrackerEntry.getActivity().getEntryID());
			pstmt.setDouble(2, activityTrackerEntry.getCalorisBurnedPerHour());
			pstmt.setTimestamp(3, activityTrackerEntry.getTimestamp());
			pstmt.setString(4, activityTrackerEntry.getStatus());
			pstmt.setInt(5, activityTrackerEntry.getUserID());
			if (activityTrackerEntry.getFbPost() != null)
				pstmt.setInt(6, activityTrackerEntry.getFbPost().getId());
			else
				pstmt.setInt(6, -1);
			if (activityTrackerEntry.getImage().getFileName() == null) {
				String encodedImage = activityTrackerEntry.getImage()
						.getEncodedImage();
				String fileName = ImageHandler
						.saveImage_ReturnFilePath(encodedImage);
				activityTrackerEntry.getImage().setFileName(fileName);
			}
			pstmt.setString(7, activityTrackerEntry.getImage().getFileName());

			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
	}

	@Override
	public void edit(ActivityTrackerEntry object) throws DataAccessException,
			EntryNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ActivityTrackerEntry object) throws DataAccessException,
			EntryNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<ActivityTrackerEntry> getAll(String userAccessToken)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getEntryId(ActivityTrackerEntry object)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void addActivityListEntry(Activity activity) throws DataAccessException {
		
		try {
			Connection conn = getConnection();
			String query = "INSERT INTO activitylist(name, MET) VALUES (?, ?)";
			PreparedStatement pstmt;

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, activity.getName());
			pstmt.setDouble(2, activity.getMET());
		
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
		
	}
}
