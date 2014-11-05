package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import phr.dao.ActivityDao;
import phr.dao.FoodDao;
import phr.dao.RestaurantDao;
import phr.dao.SportEstablishmentDao;
import phr.dao.UserDao;
import phr.dao.VerificationDao;
import phr.dao.WeightTrackerDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.models.Activity;
import phr.models.FBPost;
import phr.models.Food;
import phr.models.PHRImage;
import phr.models.PHRImageType;
import phr.models.UnverifiedActivityEntry;
import phr.models.UnverifiedFoodEntry;
import phr.models.UnverifiedRestaurantEntry;
import phr.models.UnverifiedSportsEstablishmentEntry;
import phr.models.User;
import phr.tools.ImageHandler;
import phr.tools.WeightConverter;

@Repository("verificationDao")
public class VerificationDaoImpl extends BaseDaoSqlImpl implements
		VerificationDao {
	/*
	 @Autowired
	 UserDao userDao;
	 @Autowired
	 ActivityDao activityDao;
	 @Autowired
	 WeightTrackerDao weightTrackerDao;
	 @Autowired
	 FoodDao foodDao;
	*/
	
	UserDao userDao = new UserDaoSqlImpl();
	FoodDao foodDao = new FoodDaoSqlImpl();
	ActivityDao activityDao = new ActivityDaoSqlImpl();
	WeightTrackerDao weightTrackerDao = new WeightTrackerDaoSqlImpl();
	SportEstablishmentDao sportEstablishmentDao = new SportEstablishmentDaoSqlImpl();
	RestaurantDao restaurantDao = new RestaurantDaoSqlImpl();

	final int ONE_HOUR = 3600;
	final double ONE_SERVING = 1.0;
	final String SERVING = "serving";

	@Override
	public void addNewUnverifiedPosts(String userAccessToken,
			List<FBPost> newFbPosts) throws DataAccessException {

		for (FBPost fbPost : newFbPosts) {
			switch (fbPost.getPostType()) {

			// continue on...
			case FOOD:
				addNewFoodVerificationEntry(fbPost, userAccessToken);
				break;
			case RESTAURANT:
				addNewRestaurantEntry(fbPost, userAccessToken);
				break;
			case ACTIVITY:
				addNewActivityEntry(fbPost, userAccessToken);
				break;
			case SPORTS_ESTABLISHMENTS:
				addNewSportsEstablishmentEntry(fbPost, userAccessToken);
				break;
			default:
				break;
			}
		}
	}

	private void addNewSportsEstablishmentEntry(FBPost fbPost, String userAccessToken) throws DataAccessException {

		for (String extractedWord : fbPost.getExtractedWords()) {
			UnverifiedSportsEstablishmentEntry unverifiedSportsEstablishmentEntry = new UnverifiedSportsEstablishmentEntry(
					new User(userDao.getUserIDGivenAccessToken(userAccessToken)),
					fbPost.getFacebookId(),
					fbPost.getTimestamp(), 
					fbPost.getStatus(), 
					fbPost.getImage(), 
					extractedWord,
					sportEstablishmentDao.getSportEstablishmentGivenGymName(extractedWord), 
					activityDao.getActivityListGivenGymName(extractedWord));

			try {
				Connection conn = getConnection();
				String query = "INSERT INTO tempsportestablishment (gymID, dateAdded, status, userID, facebookID, photo, extractedWord) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement pstmt;

				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, unverifiedSportsEstablishmentEntry.getSportEstablishment().getEntryID());
				pstmt.setTimestamp(2, unverifiedSportsEstablishmentEntry.getTimestamp());
				pstmt.setString(3, unverifiedSportsEstablishmentEntry.getStatus());
				pstmt.setInt(4, unverifiedSportsEstablishmentEntry.getUser().getId());
				pstmt.setString(5, unverifiedSportsEstablishmentEntry.getFacebookID());

				if (unverifiedSportsEstablishmentEntry.getImage() != null) {
					String encodedImage = unverifiedSportsEstablishmentEntry.getImage()
							.getEncodedImage();
					String fileName = ImageHandler
							.saveImage_ReturnFilePath(encodedImage);
					unverifiedSportsEstablishmentEntry.getImage().setFileName(fileName);
					pstmt.setString(6, unverifiedSportsEstablishmentEntry.getImage()
							.getFileName());
				} else
					pstmt.setNull(6, Types.NULL);
				
				pstmt.setString(7, unverifiedSportsEstablishmentEntry.getExtractedWord());

				pstmt.executeUpdate();
				conn.close();
			} catch (Exception e) {
				throw new DataAccessException(
						"An error has occured while trying to access data from the database",
						e);
			}
		}
	}

	private void addNewActivityEntry(FBPost fbPost, String userAccessToken)
			throws DataAccessException {

		for (String extractedWord : fbPost.extractedWords) {
		
			Double calories = (activityDao.getActivityMET(extractedWord)
					* WeightConverter.convertKgToLbs(
				    weightTrackerDao.getLatestWeight(userAccessToken).getWeightInPounds()) * ONE_HOUR);
			UnverifiedActivityEntry unverifiedActivityEntry = new UnverifiedActivityEntry(
					new User(userDao.getUserIDGivenAccessToken(userAccessToken)),
					fbPost.getFacebookId(),
					fbPost.getTimestamp(), 
					fbPost.getStatus(), 
					fbPost.getImage(), 
					activityDao.getActivityGivenName(extractedWord),
					ONE_HOUR, 
					calories,
					extractedWord);
			
			try {
				Connection conn = getConnection();
				String query = "INSERT INTO tempactivitytracker(activityID, durationInSeconds, calorieBurnedPerHour, dateAdded, status, userID, facebookID, photo, extractedWord) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement pstmt;

				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, unverifiedActivityEntry.getActivity().getEntryID());
				pstmt.setInt(2, unverifiedActivityEntry.getDurationInSeconds());
				pstmt.setDouble(3,
						unverifiedActivityEntry.getCalorieBurnedPerHour());
				pstmt.setTimestamp(4, unverifiedActivityEntry.getTimestamp());
				pstmt.setString(5, unverifiedActivityEntry.getStatus());
				pstmt.setInt(6, unverifiedActivityEntry.getUser().getId());
				pstmt.setString(7, unverifiedActivityEntry.getFacebookID());

				if (unverifiedActivityEntry.getImage() != null) {
					String encodedImage = unverifiedActivityEntry.getImage()
							.getEncodedImage();
					String fileName = ImageHandler
							.saveImage_ReturnFilePath(encodedImage);
					unverifiedActivityEntry.getImage().setFileName(fileName);
					pstmt.setString(8, unverifiedActivityEntry.getImage()
							.getFileName());
				} else
					pstmt.setNull(8, Types.NULL);
				
				pstmt.setString(9, unverifiedActivityEntry.getExtractedWord());

				pstmt.executeUpdate();
				conn.close();
			} catch (Exception e) {
				throw new DataAccessException(
						"An error has occured while trying to access data from the database",
						e);
			}
		}
	}

	private void addNewRestaurantEntry(FBPost fbPost, String userAccessToken)
			throws DataAccessException {

		for (String extractedWord : fbPost.getExtractedWords()) {
			UnverifiedRestaurantEntry unverifiedRestaurantEntry = new UnverifiedRestaurantEntry(
					ONE_HOUR, new User(
					userDao.getUserIDGivenAccessToken(userAccessToken)),
					fbPost.getFacebookId(), 
					fbPost.getTimestamp(), 
					fbPost.getStatus(), 
					fbPost.getImage(), 
					extractedWord,
					restaurantDao.getRestaurantGivenRestaurantName(extractedWord),
					foodDao.getFoodListGivenRestaurantName(extractedWord));

			try {
				Connection conn = getConnection();
				String query = "INSERT INTO temprestaurant(restaurantID, dateAdded, status, userID, facebookID, photo, extractedWord) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement pstmt;

				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, unverifiedRestaurantEntry.getRestaurant().getEntryID());
				pstmt.setTimestamp(2, unverifiedRestaurantEntry.getTimestamp());
				pstmt.setString(3, unverifiedRestaurantEntry.getStatus());
				pstmt.setInt(4, unverifiedRestaurantEntry.getUser().getId());
				pstmt.setString(5, unverifiedRestaurantEntry.getFacebookID());

				if (unverifiedRestaurantEntry.getImage() != null) {
					String encodedImage = unverifiedRestaurantEntry.getImage()
							.getEncodedImage();
					String fileName = ImageHandler
							.saveImage_ReturnFilePath(encodedImage);
					unverifiedRestaurantEntry.getImage().setFileName(fileName);
					pstmt.setString(6, unverifiedRestaurantEntry.getImage()
							.getFileName());
				} else
					pstmt.setNull(6, Types.NULL);
				
				pstmt.setString(7, unverifiedRestaurantEntry.getExtractedWord());

				pstmt.executeUpdate();
				conn.close();
			} catch (Exception e) {
				throw new DataAccessException(
						"An error has occured while trying to access data from the database",
						e);
			}
		}
	}

	private void addNewFoodVerificationEntry(FBPost fbPost, String userAccessToken) throws DataAccessException {

		for (String extractedWord : fbPost.getExtractedWords()) {
			
			UnverifiedFoodEntry unverifiedFoodEntry = new UnverifiedFoodEntry(
					new User(userDao.getUserIDGivenAccessToken(userAccessToken)),
					fbPost.getFacebookId(), 
					fbPost.getTimestamp(), 
					fbPost.getStatus(), 
					fbPost.getImage(), 
					foodDao.getFoodGivenName(extractedWord), 
				    ONE_SERVING,
				    extractedWord);
			
			try {
				Connection conn = getConnection();
				String query = "INSERT INTO tempfoodtracker(foodID, servingSize, dateAdded, status, userID, facebookID, photo, extractedWord) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement pstmt;

				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, unverifiedFoodEntry.getFood().getEntryID());
				
				pstmt.setDouble(2, unverifiedFoodEntry.getServingCount());
				pstmt.setTimestamp(3, unverifiedFoodEntry.getTimestamp());
				pstmt.setString(4, unverifiedFoodEntry.getStatus());
				pstmt.setInt(5, unverifiedFoodEntry.getUser().getId());
				pstmt.setString(6, unverifiedFoodEntry.getFacebookID());
				if (unverifiedFoodEntry.getImage() != null) {
					String encodedImage = unverifiedFoodEntry.getImage().getEncodedImage();
					String fileName = ImageHandler.saveImage_ReturnFilePath(encodedImage);
					unverifiedFoodEntry.getImage().setFileName(fileName);
					pstmt.setString(7, unverifiedFoodEntry.getImage().getFileName());
				} else
					pstmt.setNull(7, Types.NULL);
				
				pstmt.setString(8, unverifiedFoodEntry.getExtractedWord());

				pstmt.executeUpdate();
				conn.close();
			} catch (Exception e) {
				throw new DataAccessException(
						"An error has occured while trying to access data from the database",
						e);
			}
		}
	}

	@Override
	public List<UnverifiedFoodEntry> getAllUnverifiedFoodPosts(
			String userAccessToken) throws DataAccessException {

		List<UnverifiedFoodEntry> foodEntries = new ArrayList<UnverifiedFoodEntry>();
		try {
			Connection conn = getConnection();
			String query = "SELECT * FROM tempfoodtracker WHERE userID = ?";

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
				foodEntries.add(new UnverifiedFoodEntry(
						rs.getInt("id"),
						new User(rs.getInt("userID")), 
						rs.getString("facebookID"), 
						rs.getTimestamp("dateAdded"), 
						rs.getString("status"), 
						image, 
						foodDao.getFood(rs.getInt("foodID")),
						rs.getDouble("servingSize"),
						rs.getString("extractedWord")));
			}
			conn.close();
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
		return foodEntries;
	}

	@Override
	public List<UnverifiedActivityEntry> getAllUnverifiedActivityPosts(
			String userAccessToken) throws DataAccessException {

		List<UnverifiedActivityEntry> activityEntries = new ArrayList<UnverifiedActivityEntry>();
		try {
			Connection conn = getConnection();
			String query = "SELECT * FROM tempactivitytracker WHERE userID = ?";

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

				activityEntries.add(new UnverifiedActivityEntry(
						rs.getInt("id"),
						new User(rs.getInt("userID")), 
						rs.getString("facebookID"), 
						rs.getTimestamp("dateAdded"), 
						rs.getString("status"), 
						image,
						activityDao.getActivity(rs.getInt("activityID")),
						rs.getInt("durationInSeconds"),
						rs.getDouble("calorieBurnedPerHour"),
						rs.getString("extractedWord")));

			}
			conn.close();
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

		return activityEntries;
	}

	@Override
	public List<UnverifiedRestaurantEntry> getAllUnverifiedRestaurantPosts(
			String userAccessToken) throws DataAccessException {

		List<UnverifiedRestaurantEntry> restaurantEntries = new ArrayList<UnverifiedRestaurantEntry>();
		try {
			Connection conn = getConnection();
			String query = "SELECT * FROM temprestaurant WHERE userID = ?";

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

				restaurantEntries.add(new UnverifiedRestaurantEntry(
						rs.getInt("id"), 
						new User(rs.getInt("userID")), 
						rs.getString("facebookID"), 
						rs.getTimestamp("dateAdded"),
						rs.getString("status"), 
						image, 
						rs.getString("extractedWord"), 
						restaurantDao.getRestaurantGivenRestaurantID(rs.getInt("restaurantID")), 
						foodDao.getFoodListGivenRestaurantName(rs.getString("extractedWord"))));

			}
			conn.close();
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}

		return restaurantEntries;
	}

	@Override
	public List<UnverifiedSportsEstablishmentEntry> getAllUnverifiedSportsEstablishmentPosts(
			String userAccessToken) throws DataAccessException {

		List<UnverifiedSportsEstablishmentEntry> sportsEstablishmentsEntries = new ArrayList<UnverifiedSportsEstablishmentEntry>();
		try {
			Connection conn = getConnection();
			String query = "SELECT * FROM tempsportestablishment WHERE userID = ?";

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

				sportsEstablishmentsEntries.add(new UnverifiedSportsEstablishmentEntry(
						rs.getInt("id"), 
						new User(rs.getInt("userID")), 
						rs.getString("facebookID"), 
						rs.getTimestamp("dateAdded"),
						rs.getString("status"), 
						image, 
						rs.getString("extractedWord"),
						sportEstablishmentDao.getSportEstablishmentGivenGymID(rs.getInt("gymID")),
						activityDao.getActivityListGivenGymName(rs.getString("extractedWord"))));

			}
			conn.close();
		} catch (Exception e) {
			throw new DataAccessException(
					"An error has occured while trying to access data from the database",
					e);
		}
		return sportsEstablishmentsEntries;
	}

	@Override
	public void delete(UnverifiedFoodEntry entry) throws EntryNotFoundException {
		try {
			Connection conn = getConnection();
			String query = "DELETE FROM tempfoodtracker WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, entry.getEntryID());

			pstmt.executeUpdate();
			conn.close();
		} catch (Exception e) {
			throw new EntryNotFoundException(
					"Object ID not found in the database", e);
		}
	}

	@Override
	public void delete(UnverifiedActivityEntry entry)
			throws EntryNotFoundException {
		try {
			Connection conn = getConnection();
			String query = "DELETE FROM tempactivitytracker WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, entry.getEntryID());

			pstmt.executeUpdate();
			conn.close();
		} catch (Exception e) {
			throw new EntryNotFoundException(
					"Object ID not found in the database", e);
		}
	}

	@Override
	public void delete(UnverifiedRestaurantEntry entry)
			throws EntryNotFoundException {
		try {
			Connection conn = getConnection();
			String query = "DELETE FROM temprestaurant WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, entry.getEntryID());

			pstmt.executeUpdate();
			conn.close();
		} catch (Exception e) {
			throw new EntryNotFoundException(
					"Object ID not found in the database", e);
		}
	}

	@Override
	public void delete(UnverifiedSportsEstablishmentEntry entry)
			throws EntryNotFoundException {
		try {
			Connection conn = getConnection();
			String query = "DELETE FROM tempsportestablishment WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, entry.getEntryID());

			pstmt.executeUpdate();
			conn.close();
		} catch (Exception e) {
			throw new EntryNotFoundException(
					"Object ID not found in the database", e);
		}
	}
}
