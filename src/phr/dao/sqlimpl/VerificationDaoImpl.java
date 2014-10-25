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

	// @Autowired
	// ActivityDao activityDao;

	// @Autowired
	// WeightTrackerDao weightTrackerDao;

	// @Autowired
	// FoodDao foodDao;

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
				addNewSportsEstablishmentEntry(fbPost);
				break;
			default:
				break;
			}
		}
	}

	private void addNewSportsEstablishmentEntry(FBPost fbPost) {
		// TODO Auto-generated method stub

	}

	private void addNewActivityEntry(FBPost fbPost, String userAccessToken)
			throws DataAccessException {

		for (String extractedWord : fbPost.extractedWords) {
			Double calories = (activityDao.getActivityMET(extractedWord)
					* WeightConverter.convertKgToLbs(weightTrackerDao
							.getLatestWeight(userAccessToken)
							.getWeightInPounds()) * ONE_HOUR);
			UnverifiedActivityEntry unverifiedActivityEntry = new UnverifiedActivityEntry(
					new User(userDao.getUserIDGivenAccessToken(userAccessToken)),
					fbPost.getFacebookId(), fbPost.getTimestamp(), fbPost
							.getStatus(), fbPost.getImage(), extractedWord,
					ONE_HOUR, calories);

			try {
				Connection conn = getConnection();
				String query = "INSERT INTO tempactivitytracker(activityName, durationInSeconds, calorieBurnedPerHour, dateAdded, status, userID, facebookID, photo) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement pstmt;

				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, unverifiedActivityEntry.getActivityName());
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
							.getStatus(), fbPost.getImage(), extractedWord,
					food.getCalorie(), food.getProtein(), food.getFat(), food
							.getCarbohydrate(), SERVING, ONE_SERVING);

			try {
				Connection conn = getConnection();
				String query = "INSERT INTO tempfoodtracker(foodName, calorie, protein, fat, carbohydrate, servingUnit, servingSize, dateAdded, status, userID, facebookID, photo) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement pstmt;

				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, unverifiedFoodEntry.getFoodName());
				pstmt.setDouble(2, unverifiedFoodEntry.getCalorie());
				pstmt.setDouble(3, unverifiedFoodEntry.getProtein());
				pstmt.setDouble(4, unverifiedFoodEntry.getFat());
				pstmt.setDouble(5, unverifiedFoodEntry.getCarbohydrate());
				pstmt.setString(6, unverifiedFoodEntry.getServingUnit());
				pstmt.setDouble(7, unverifiedFoodEntry.getServingSize());
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
				foodEntries.add(new UnverifiedFoodEntry(rs.getInt("id"),
						new User(rs.getInt("userID")), rs
								.getString("facebookID"), rs
								.getTimestamp("dateAdded"), rs
								.getString("status"), image, rs
								.getString("foodName"),
						rs.getDouble("calorie"), rs.getDouble("protein"), rs
								.getDouble("fat"),
						rs.getDouble("carbohydrate"), rs
								.getString("servingUnit"), rs
								.getDouble("servingSize")));
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
								.getString("status"), image, rs
								.getString("activityName"), rs
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
						rs.getString("status"), image, rs.getString("name")));

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
			String userAccessToken) {

		return null;
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
			// pstmt.setInt(1, entry.getEntryID());

			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new EntryNotFoundException(
					"Object ID not found in the database", e);
		}
	}
}
