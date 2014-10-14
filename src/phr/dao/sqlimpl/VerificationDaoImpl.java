package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import phr.dao.ActivityDao;
import phr.dao.UserDao;
import phr.dao.VerificationDao;
import phr.exceptions.DataAccessException;
import phr.models.Activity;
import phr.models.ActivityTrackerEntry;
import phr.models.FBPost;
import phr.models.User;
import phr.tools.ImageHandler;

public class VerificationDaoImpl extends BaseDaoSqlImpl implements VerificationDao {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	ActivityDao activityDao;

	@Override
	public void setNewUnverifiedPosts(String userAccessToken,
			List<FBPost> newFbPosts) {

		for (FBPost fbPost : newFbPosts) {
			switch (fbPost.getPostType()) {

			// continue on...
			case FOOD:
				createNewFoodVerificationEntry(fbPost);
				break;
			case RESTAURANT:
				createNewRestaurantEntry(fbPost);
				break;
			case ACTIVITY:
				try {
					createNewActivityEntry(fbPost, userAccessToken);
				} catch (DataAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case SPORTS_ESTABLISHMENTS:
				createNewSportsEstablishmentEntry(fbPost);
				break;
			default:
				break;
			}
		}
	}

	private void createNewSportsEstablishmentEntry(FBPost fbPost) {
		// TODO Auto-generated method stub

	}

	private void createNewActivityEntry(FBPost fbPost, String userAccessToken) throws DataAccessException {
		
		for(String extractedWord : fbPost.extractedWords){
			
			Timestamp timestamp = null;
			ActivityTrackerEntry activityTrackerEntry = new ActivityTrackerEntry(
					new User(userDao.getUserIDGivenAccessToken(userAccessToken)),
					fbPost,
					timestamp,
					//fbPost.getDatetime(),
					fbPost.getStatus(),
					fbPost.getImage(),
					new Activity(extractedWord, activityDao.getActivityMET(extractedWord)),
					0.0,
					0);
			
			try {
				Connection conn = getConnection();
				String query = "INSERT INTO activitytracker(activityName, MET, durationInSeconds, calorieBurnedPerHour, dateAdded, status, userID, fbPostID, photo) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement pstmt;

				pstmt = conn.prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, activityTrackerEntry.getActivity().getName());
				pstmt.setDouble(2, activityTrackerEntry.getActivity().getMET());
				pstmt.setInt(3, activityTrackerEntry.getDurationInSeconds());
				pstmt.setDouble(4, activityTrackerEntry.getCalorisBurnedPerHour());
				pstmt.setTimestamp(5, activityTrackerEntry.getTimestamp());
				pstmt.setString(6, activityTrackerEntry.getStatus());
				pstmt.setInt(7, activityTrackerEntry.getUserID());
				if (activityTrackerEntry.getFbPost() != null)
					pstmt.setInt(8, activityTrackerEntry.getFbPost().getId());
				else
					pstmt.setNull(8, Types.NULL);
				
				if (activityTrackerEntry.getImage()!= null) {
					String encodedImage = activityTrackerEntry.getImage()
							.getEncodedImage();
					String fileName = ImageHandler
							.saveImage_ReturnFilePath(encodedImage);
					activityTrackerEntry.getImage().setFileName(fileName);
					pstmt.setString(9, activityTrackerEntry.getImage().getFileName());
				}
				else
					pstmt.setNull(9, Types.NULL);

				pstmt.executeUpdate();

			} catch (Exception e) {
				throw new DataAccessException(
						"An error has occured while trying to access data from the database",
						e);
			}
		}
	}

	private void createNewRestaurantEntry(FBPost fbPost) {
		// TODO Auto-generated method stub

	}

	private void createNewFoodVerificationEntry(FBPost fbPost) {
		// TODO Auto-generated method stub

	}
}
