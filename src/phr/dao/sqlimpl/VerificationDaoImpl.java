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

	// @Autowired
	// UserDao userDao;
	/*
	 * @Autowired ActivityDao activityDao;
	 * 
	 * @Autowired WeightTrackerDao weightTrackerDao;
	 * 
	 * @Autowired FoodDao foodDao;
	 */

	UserDao userDao = new UserDaoSqlImpl();
	FoodDao foodDao = new FoodDaoSqlImpl();
	ActivityDao activityDao = new ActivityDaoSqlImpl();
	WeightTrackerDao weightTrackerDao = new WeightTrackerDaoSqlImpl();

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

	private void addNewSportsEstablishmentEntry(FBPost fbPost,
			String userAccessToken) throws DataAccessException {

		for (String extractedWord : fbPost.getExtractedWords()) {
			UnverifiedSportsEstablishmentEntry unverifiedSportsEstablishmentEntry = new UnverifiedSportsEstablishmentEntry(
					new User(userDao.getUserIDGivenAccessToken(userAccessToken)),
					fbPost.getFacebookId(), fbPost.getTimestamp(), fbPost
							.getStatus(), fbPost.getImage(), extractedWord);

			try {
				Connection conn = getConnection();
				String query = "INSERT INTO tempsportestablishment (gymName, dateAdded, status, userID, facebookID, photo) "
						+ "VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement pstmt;

				pstmt = conn.prepareStatement(query);
				pstmt.setString(1,
						unverifiedSportsEstablishmentEntry.getGymName());
				pstmt.setTimestamp(2,
						unverifiedSportsEstablishmentEntry.getTimestamp());
				pstmt.setString(3,
						unverifiedSportsEstablishmentEntry.getStatus());
				pstmt.setInt(4, unverifiedSportsEstablishmentEntry.getUser()
						.getId());
				pstmt.setString(5,
						unverifiedSportsEstablishmentEntry.getFacebookID());

				if (unverifiedSportsEstablishmentEntry.getImage() != null) {
					String encodedImage = unverifiedSportsEstablishmentEntry
							.getImage().getEncodedImage();
					String fileName = ImageHandler
							.saveImage_ReturnFilePath(encodedImage);
					unverifiedSportsEstablishmentEntry.getImage().setFileName(
							fileName);
					pstmt.setString(6, unverifiedSportsEstablishmentEntry
							.getImage().getFileName());
				} else
					pstmt.setNull(6, Types.NULL);

				pstmt.executeUpdate();

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

			Activity activity = activityDao.search(extractedWord).get(0);
			Double calories = (activityDao.getActivityMET(extractedWord)
					* WeightConverter.convertKgToLbs(weightTrackerDao
							.getLatestWeight(userAccessToken)
							.getWeightInPounds()) * ONE_HOUR);
			UnverifiedActivityEntry unverifiedActivityEntry = new UnverifiedActivityEntry(
					new User(userDao.getUserIDGivenAccessToken(userAccessToken)),
					fbPost.getFacebookId(), fbPost.getTimestamp(), fbPost
							.getStatus(), fbPost.getImage(), activity,
					ONE_HOUR, calories);

			try {
				Connection conn = getConnection();
				String query = "INSERT INTO tempactivitytracker(activityName, durationInSeconds, calorieBurnedPerHour, dateAdded, status, userID, facebookID, photo) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement pstmt;

				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, unverifiedActivityEntry.getActivity()
						.getName());
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

				pstmt.executeUpdate();

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
					new User(userDao.getUserIDGivenAccessToken(userAccessToken)),
					fbPost.getFacebookId(), fbPost.getTimestamp(), fbPost
							.getStatus(), fbPost.getImage(), extractedWord);

			try {
				Connection conn = getConnection();
				String query = "INSERT INTO temprestaurant(restaurantName, dateAdded, status, userID, facebookID, photo) "
						+ "VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement pstmt;

				pstmt = conn.prepareStatement(query);
				pstmt.setString(1,
						unverifiedRestaurantEntry.getRestaurantName());
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

				pstmt.executeUpdate();

			} catch (Exception e) {
				throw new DataAccessException(
						"An error has occured while trying to access data from the database",
						e);
			}
		}
	}

	private void addNewFoodVerificationEntry(FBPost fbPost,
			String userAccessToken) throws DataAccessException {

		for (String extractedWord : fbPost.getExtractedWords()) {

			Food food = foodDao.search(extractedWord).get(0);

			UnverifiedFoodEntry unverifiedFoodEntry = new UnverifiedFoodEntry(
					new User(userDao.getUserIDGivenAccessToken(userAccessToken)),
					fbPost.getFacebookId(), fbPost.getTimestamp(), fbPost
							.getStatus(), fbPost.getImage(), food, ONE_SERVING);

			try {
				Connection conn = getConnection();
				String query = "INSERT INTO tempfoodtracker(foodName, calorie, protein, fat, carbohydrate, servingUnit, servingSize, dateAdded, status, userID, facebookID, photo) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement pstmt;

				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, unverifiedFoodEntry.getFood().getName());
				pstmt.setDouble(2, unverifiedFoodEntry.getFood().getCalorie());
				pstmt.setDouble(3, unverifiedFoodEntry.getFood().getProtein());
				pstmt.setDouble(4, unverifiedFoodEntry.getFood().getFat());
				pstmt.setDouble(5, unverifiedFoodEntry.getFood()
						.getCarbohydrate());
				pstmt.setString(6, unverifiedFoodEntry.getFood()
						.getServingUnit());
				pstmt.setDouble(7, unverifiedFoodEntry.getServingCount());
				pstmt.setTimestamp(8, unverifiedFoodEntry.getTimestamp());
				pstmt.setString(9, unverifiedFoodEntry.getStatus());
				pstmt.setInt(10, unverifiedFoodEntry.getUser().getId());
				pstmt.setString(11, unverifiedFoodEntry.getFacebookID());
				if (unverifiedFoodEntry.getImage() != null) {
					String encodedImage = unverifiedFoodEntry.getImage()
							.getEncodedImage();
					String fileName = ImageHandler
							.saveImage_ReturnFilePath(encodedImage);
					unverifiedFoodEntry.getImage().setFileName(fileName);
					pstmt.setString(12, unverifiedFoodEntry.getImage()
							.getFileName());
				} else
					pstmt.setNull(12, Types.NULL);

				pstmt.executeUpdate();

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
				foodEntries
						.add(new UnverifiedFoodEntry(rs.getInt("id"), new User(
								rs.getInt("userID")), rs
								.getString("facebookID"), rs
								.getTimestamp("dateAdded"), rs
								.getString("status"), image, foodDao.getFood(rs
								.getInt("foodID")), rs.getDouble("servingSize")));
			}
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
						rs.getInt("id"), new User(rs.getInt("userID")), rs
								.getString("facebookID"), rs
								.getTimestamp("dateAdded"), rs
								.getString("status"), image, activityDao
								.getActivity(rs.getInt("activityID")), rs
								.getInt("durationInSeconds"), rs
								.getDouble("calorieBurnedPerHour")));

			}
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

				restaurantEntries.add(new UnverifiedRestaurantEntry(rs
						.getInt("id"), new User(rs.getInt("userID")), rs
						.getString("facebookID"), rs.getTimestamp("dateAdded"),
						rs.getString("status"), image, rs
								.getString("restaurantName")));

			}
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

				sportsEstablishmentsEntries
						.add(new UnverifiedSportsEstablishmentEntry(rs
								.getInt("id"), new User(rs.getInt("userID")),
								rs.getString("facebookID"), rs
										.getTimestamp("dateAdded"), rs
										.getString("status"), image, rs
										.getString("gymName")));

			}
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
			String query = "DELETE FROM tempfoodtrcker WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, entry.getEntryID());

			pstmt.executeUpdate();

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
			String query = "DELETE FROM tempactivitytracker WHERE id = ?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, entry.getEntryID());

			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new EntryNotFoundException(
					"Object ID not found in the database", e);
		}
	}
}
