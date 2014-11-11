package phr.sns.datamining.daoimpl;

import java.awt.Image;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import phr.exceptions.DataAccessException;
import phr.exceptions.ImageHandlerException;
import phr.exceptions.ServiceException;
import phr.models.FBPost;
import phr.models.FBPostType;
import phr.models.PHRImage;
import phr.models.PHRImageType;
import phr.service.FacebookPostService;
import phr.service.impl.FacebookPostServiceImpl;
import phr.sns.datamining.dao.FacebookFetcherDao;
import phr.sns.datamining.filter.KeywordsExtractor;
import phr.tools.ImageHandler;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.PagableList;
import facebook4j.Paging;
import facebook4j.Photo;
import facebook4j.PictureSize;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.auth.AccessToken;

@Repository("facebookFetcherDao")
public class FacebookFetcherDaoImpl implements FacebookFetcherDao {

	private final KeywordsExtractor keywordsExtractor = new KeywordsExtractor();
	private static final String appID = "458502710946706";
	private static final String appSecret = "c41ccfbd5ff58c87342f4df5911d2d88";
	int foodCount = 0, actCount = 0, restoCount = 0, seCount = 0, unrCount = 0;
	Facebook facebook;

	File file = new File("C:/Users/Matthew/Desktop/perrin.txt");
	BufferedWriter out;

	public FacebookFetcherDaoImpl() {
		try {
			out = new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			e.printStackTrace();
		}

		facebook = new FacebookFactory().getInstance();
		facebook.setOAuthAppId(appID, appSecret);
		String permissions = "email,user_groups,user_status,read_stream, user_photos, user_actions:instapp";
		facebook.setOAuthPermissions(permissions);
	}

	private List<Post> getAllPostsFromFB(String userFBAccessToken,
			Timestamp timestamp) throws DataAccessException {
		boolean toFetch = true;
		facebook.setOAuthAccessToken(new AccessToken(userFBAccessToken, null));
		try {
			List<Post> completeList = new ArrayList<>();
			Reading reading = new Reading();
			reading.since(new Date(timestamp.getTime()));
			System.out.println("READING POSTS: " + timestamp);
			PagableList<Post> feed = facebook.getPosts(reading);
			Paging<Post> paging = null;
			do {
				for (Post post : feed) {
					if (post.getCreatedTime().before(
							new Date(timestamp.getTime())))
						toFetch = false;

					if (toFetch == true)
						completeList.add(post);
				}
				paging = feed.getPaging();
			} while ((paging != null)
					&& (feed = facebook.fetchNext(paging)) != null
					&& toFetch == true);
			System.out.println("Number of Posts Retrieved: "
					+ completeList.size());
			out.write("Number of Posts Retrieved: " + completeList.size()
					+ "\t\n");
			return completeList;
		} catch (FacebookException | IOException e) {
			throw new DataAccessException(
					"An error has occured while retrieving posts", e);
		}
	}

	private List<Photo> getAllPhotosFromFB(String userFBAccessToken,
			Timestamp timestamp) throws DataAccessException {
		boolean toFetch = true;
		facebook.setOAuthAccessToken(new AccessToken(userFBAccessToken, null));
		try {
			List<Photo> completeList = new ArrayList<>();
			// PagableList<Photo> photos = facebook.getPhotos();
			Reading reading = new Reading();
			reading.since(new Date(timestamp.getTime()));
			System.out.println("READING PHOTO: " + timestamp);
			PagableList<Photo> photos = facebook.getUploadedPhotos(reading);
			Paging<Photo> paging = null;
			do {
				for (Photo photo : photos) {
					if (photo.getCreatedTime().before(
							new Date(timestamp.getTime())))
						toFetch = false;

					if (toFetch == true)
						completeList.add(photo);
				}
				paging = photos.getPaging();
			} while ((paging != null)
					&& (photos = facebook.fetchNext(paging)) != null
					&& toFetch == true);
			System.out.println("Number of Photos Retrieved: "
					+ completeList.size());
			out.write("Number of Photos Retrieved: " + completeList.size()
					+ "\t\n");
			return completeList;
		} catch (FacebookException | IOException e) {
			throw new DataAccessException(
					"An error has occured while retrieving photos", e);
		}

	}

	private List<FBPost> getAllPosts(String userFBAccessToken,
			Timestamp timestamp) throws DataAccessException {
		List<Post> feed = getAllPostsFromFB(userFBAccessToken, timestamp);
		List<FBPost> filteredPosts = new ArrayList<>();
		try {
			filteredPosts = filterPostsList(feed);
		} catch (ImageHandlerException | FacebookException e) {
			throw new DataAccessException(
					"An error has occured while retrieving posts", e);
		}

		return filteredPosts;
	}

	private List<FBPost> filterPostsList(List<Post> feed)
			throws ImageHandlerException, DataAccessException,
			FacebookException {

		List<FBPost> posts = null;
		try {
			posts = new ArrayList<>();

			for (Post p : feed) {
				if (p != null) {
					String message = p.getMessage();
					boolean matchFound = false;

					Date date = p.getCreatedTime();
					Timestamp timestamp = new Timestamp(date.getTime());
					FBPost post;
					String encodedImage = null;
					PHRImage phrImage = null;
					if (p.getPicture() != null) {
						String id = p.getId();
						URL newURL = facebook.getPictureURL(id,
								PictureSize.normal);
						Image imageFromPost = ImageHandler
								.getImageFromURL(newURL);
						// Image imageFromPost =
						// ImageHandler.getImageFromURL(p.getPicture());
						encodedImage = ImageHandler
								.encodeImageToBase64(imageFromPost);
						if (encodedImage != null)
							phrImage = new PHRImage(encodedImage,
									PHRImageType.IMAGE);
					}

					if (p.getPlace() != null)
						message = message + " " + p.getPlace().getName();

					if (p.getMessage() != null) {
						String[] foodWordsFound = keywordsExtractor
								.extractFoodNames(message);
						String[] restaurantsWordsFound = keywordsExtractor
								.extractRestaurantNames(message);
						if (foodWordsFound.length > 0) {
							matchFound = true;
							post = new FBPost(p.getId(), message, timestamp,
									FBPostType.FOOD, phrImage, foodWordsFound);
							posts.add(post);
							System.out.println(message);
							System.out.println("Class: FOOD");
							out.write(message + "\t\n");
							out.write("Class: FOOD" + "\t\n");
							for (String s : foodWordsFound) {
								System.out.println(s);
								out.write(s + "\t\n");
							}
							foodCount++;
							continue;
						} else if (restaurantsWordsFound.length > 0) {
							matchFound = true;
							post = new FBPost(p.getId(), message, timestamp,
									FBPostType.RESTAURANT, phrImage,
									restaurantsWordsFound);
							posts.add(post);
							System.out.println(message);
							System.out.println("Class: RESTAURANT");
							out.write(message + "\t\n");
							out.write("Class: RESTAURANT" + "\t\n");
							for (String s : restaurantsWordsFound) {
								System.out.println(s);
								out.write(s + "\t\n");
							}
							restoCount++;
							continue;
						}

						String[] activityWordsFound = keywordsExtractor
								.extractActivityNames(message);
						String[] sportsEstablishmentsWordsFound = keywordsExtractor
								.extractSportsEstablishmentsNames(message);
						if (activityWordsFound.length > 0) {
							matchFound = true;
							post = new FBPost(p.getId(), p.getMessage(),
									timestamp, FBPostType.ACTIVITY, phrImage,
									activityWordsFound);
							posts.add(post);
							System.out.println(message);
							System.out.println("Class: ACTIVITY");
							out.write(message + "\t\n");
							out.write("Class: ACTIVITY" + "\t\n");
							for (String s : activityWordsFound) {
								System.out.println(s);
								out.write(s + "\t\n");
							}
							actCount++;
							continue;
						} else if (sportsEstablishmentsWordsFound.length > 0) {
							matchFound = true;
							post = new FBPost(p.getId(), p.getMessage(),
									timestamp,
									FBPostType.SPORTS_ESTABLISHMENTS, phrImage,
									sportsEstablishmentsWordsFound);
							posts.add(post);
							System.out.println(message);
							System.out.println("Class: SPORTS ESTABLISHMENT");
							out.write(message + "\t\n");
							out.write("Class: SPORTS ESTABLISHMENT" + "\t\n");
							for (String s : sportsEstablishmentsWordsFound) {
								System.out.println(s);
								out.write(s + "\t\n");
							}
							seCount++;
							continue;
						}

						if (matchFound == false) {
							post = new FBPost(p.getId(), message, timestamp,
									FBPostType.UNRELATED, phrImage, null);
							posts.add(post);
							System.out.println(message);
							System.out.println("Class: UNRELATED");
							out.write(message + "\t\n");
							out.write("Class: UNRELATED" + "\t\n");
							unrCount++;
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return posts;
	}

	private List<FBPost> filterPhotosList(List<Photo> newPhotos)
			throws ImageHandlerException, DataAccessException,
			FacebookException {
		List<FBPost> posts = null;
		try {
			posts = new ArrayList<>();
			for (Photo p : newPhotos) {
				if (p != null) {
					String message = p.getName();

					boolean matchFound = false;

					Date date = p.getCreatedTime();
					Timestamp timestamp = new Timestamp(date.getTime());
					FBPost post;
					String encodedImage = null;
					PHRImage phrImage = null;
					if (p.getPicture() != null) {
						String id = p.getId();
						URL newURL = facebook.getPictureURL(id,
								PictureSize.normal);
						Image imageFromPost = ImageHandler
								.getImageFromURL(newURL);
						// Image imageFromPost =
						// ImageHandler.getImageFromURL(p.getPicture());
						encodedImage = ImageHandler
								.encodeImageToBase64(imageFromPost);
						if (encodedImage != null)
							phrImage = new PHRImage(encodedImage,
									PHRImageType.IMAGE);
					}

					if (p.getPlace() != null)
						message = message + " " + p.getPlace().getName();

					if (p.getName() != null) {
						String[] foodWordsFound = keywordsExtractor
								.extractFoodNames(message);
						String[] restaurantsWordsFound = keywordsExtractor
								.extractRestaurantNames(message);
						if (foodWordsFound.length > 0) {
							matchFound = true;
							post = new FBPost(p.getId(), message, timestamp,
									FBPostType.FOOD, phrImage, foodWordsFound);
							posts.add(post);
							System.out.println(message);
							System.out.println("Class: FOOD");
							out.write(message + "\t\n");
							out.write("Class: FOOD" + "\t\n");
							for (String s : foodWordsFound) {
								System.out.println(s);
								out.write(s + "\t\n");
							}
							foodCount++;
							continue;
						} else if (restaurantsWordsFound.length > 0) {
							matchFound = true;
							post = new FBPost(p.getId(), message, timestamp,
									FBPostType.RESTAURANT, phrImage,
									restaurantsWordsFound);
							posts.add(post);
							System.out.println(message);
							System.out.println("Class: RESTAURANT");
							out.write(message);
							out.write("Class: RESTAURANT");
							for (String s : restaurantsWordsFound) {
								System.out.println(s + "\t\n");
								out.write(s + "\t\n");
							}
							restoCount++;
							continue;
						}

						String[] activityWordsFound = keywordsExtractor
								.extractActivityNames(message);
						String[] sportsEstablishmentsWordsFound = keywordsExtractor
								.extractSportsEstablishmentsNames(message);
						if (activityWordsFound.length > 0) {
							matchFound = true;
							post = new FBPost(p.getId(), p.getName(),
									timestamp, FBPostType.ACTIVITY, phrImage,
									activityWordsFound);
							posts.add(post);
							System.out.println(message);
							System.out.println("Class: ACTIVITY");
							out.write(message + "\t\n");
							out.write("Class: ACTIVITY" + "\t\n");
							for (String s : activityWordsFound) {
								System.out.println(s);
								out.write(s + "\t\n");
							}
							actCount++;
							continue;
						} else if (sportsEstablishmentsWordsFound.length > 0) {
							matchFound = true;
							post = new FBPost(p.getId(), p.getName(),
									timestamp,
									FBPostType.SPORTS_ESTABLISHMENTS, phrImage,
									sportsEstablishmentsWordsFound);
							posts.add(post);
							System.out.println(message);
							System.out.println("Class: SPORTS ESTABLISHMENTS");
							out.write(message + "\t\n");
							out.write("Class: SPORTS ESTABLISHMENT" + "\t\n");
							for (String s : sportsEstablishmentsWordsFound) {
								System.out.println(s);
								out.write(s + "\t\n");
							}
							seCount++;
							continue;
						}

						if (matchFound == false) {
							post = new FBPost(p.getId(), message, timestamp,
									FBPostType.UNRELATED, phrImage, null);
							posts.add(post);
							System.out.println(message);
							System.out.println("Class: UNRELATED");
							out.write(message + "\t\n");
							out.write("Class: UNRELATED" + "\t\n");
							unrCount++;
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return posts;
	}

	@Override
	public List<FBPost> getNewPosts(Timestamp timestamp,
			String userFBAccessToken, String userAccessToken)
			throws DataAccessException {
		List<Post> feed = getAllPostsFromFB(userFBAccessToken, timestamp);
		List<Post> newFeed = new ArrayList<>();
		try {
			FacebookPostService fbPostService = new FacebookPostServiceImpl();
			List<String> idsInDb = fbPostService
					.getAllFacebookID(userAccessToken);

			for (Post p : feed) {
				if (p != null) {
					Date dateLastUpdated = p.getUpdatedTime();
					Timestamp timeLastUpdated = new Timestamp(
							dateLastUpdated.getTime());
					if (timeLastUpdated.after(timestamp)
							&& noDuplicateInDB(p.getId(), idsInDb)) {
						newFeed.add(p);
					}
				}
			}

			List<Photo> photos = getAllPhotosFromFB(userFBAccessToken,
					timestamp);
			List<Photo> newPhotos = new ArrayList<>();

			for (Photo p : photos) {
				if (p != null) {
					Date dateLastUpdated = p.getUpdatedTime();
					Timestamp timeLastUpdated = new Timestamp(
							dateLastUpdated.getTime());
					if (timeLastUpdated.after(timestamp)
							&& noDuplicateInDB(p.getId(), idsInDb)) {
						newPhotos.add(p);
					}
				}
			}

			List<FBPost> filteredPosts = filterPostsList(newFeed);
			List<FBPost> filteredPhotos = filterPhotosList(newPhotos);
			System.out.println("F: " + foodCount + " A: " + actCount + " R: "
					+ restoCount);
			System.out.println("S: " + seCount + " U: " + unrCount);
			out.write("F: " + foodCount + " A: " + actCount + " R: "
					+ restoCount + "\t\n");
			out.write("S: " + seCount + " U: " + unrCount + "\t\n");
			out.flush();
			out.close();
			filteredPosts.addAll(filteredPhotos);
			return filteredPosts;
		} catch (ImageHandlerException | ServiceException | FacebookException
				| IOException e) {
			throw new DataAccessException(
					"An error has occured while retrieving posts", e);
		}
	}

	private boolean noDuplicateInDB(String id, List<String> idsInDb) {
		for (String s : idsInDb) {
			if (s != null && s.equals(id))
				return false;
		}
		return true;
	}
}
