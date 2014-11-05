package phr.sns.datamining.daoimpl;

import java.awt.Image;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.auth.AccessToken;

@Repository("facebookFetcherDao")
public class FacebookFetcherDaoImpl implements FacebookFetcherDao {

	private final KeywordsExtractor keywordsExtractor = new KeywordsExtractor();
	private static final String appID = "458502710946706";
	private static final String appSecret = "c41ccfbd5ff58c87342f4df5911d2d88";

	Facebook facebook;

	public FacebookFetcherDaoImpl() {
		facebook = new FacebookFactory().getInstance();
		facebook.setOAuthAppId(appID, appSecret);
		String permissions = "email,user_groups,user_status,read_stream, user_photos, user_actions:instapp";
		facebook.setOAuthPermissions(permissions);
	}

	private List<Post> getAllPostsFromFB(String userFBAccessToken)
			throws DataAccessException {
		facebook.setOAuthAccessToken(new AccessToken(userFBAccessToken, null));
		try {
			List<Post> completeList = new ArrayList<>();
			PagableList<Post> feed = facebook.getPosts();
			Paging<Post> paging = null;
			do {
				for (Post post : feed) {
					completeList.add(post);
				}
				paging = feed.getPaging();
			} while ((paging != null)
					&& (feed = facebook.fetchNext(paging)) != null);
			System.out.println("Number of Posts Retrieved: "
					+ completeList.size());
			return completeList;
		} catch (FacebookException  e) {
			throw new DataAccessException(
					"An error has occured while retrieving posts", e);
		}
	}

	private List<Photo> getAllPhotosFromFB(String userFBAccessToken)
			throws DataAccessException {
		facebook.setOAuthAccessToken(new AccessToken(userFBAccessToken, null));
		try {
			List<Photo> completeList = new ArrayList<>();
			//PagableList<Photo> photos = facebook.getPhotos();
			PagableList<Photo> photos = facebook.getUploadedPhotos();
			Paging<Photo> paging = null;
			do {
				for (Photo photo : photos) {
					completeList.add(photo);
				}
				paging = photos.getPaging();
			} while ((paging != null)
					&& (photos = facebook.fetchNext(paging)) != null);
			System.out.println("Number of Photos Retrieved: "
					+ completeList.size());
			return completeList;
		} catch (FacebookException e) {
			throw new DataAccessException(
					"An error has occured while retrieving photos", e);
		}

	}

	@Override
	public List<FBPost> getAllPosts(String userFBAccessToken)
			throws DataAccessException {
		List<Post> feed = getAllPostsFromFB(userFBAccessToken);
		List<FBPost> filteredPosts = new ArrayList<>();
		try {
			filteredPosts = filterPostsList(feed);
		} catch (ImageHandlerException e) {
			throw new DataAccessException(
					"An error has occured while retrieving posts", e);
		}

		return filteredPosts;
	}

	private List<FBPost> filterPostsList(List<Post> feed)
			throws ImageHandlerException, DataAccessException {

		List<FBPost> posts = new ArrayList<>();

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
					Image imageFromPost = ImageHandler.getImageFromURL(p
							.getPicture());
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
						continue;
					} else if (restaurantsWordsFound.length > 0) {
						matchFound = true;
						post = new FBPost(p.getId(), message, timestamp,
								FBPostType.RESTAURANT, phrImage,
								restaurantsWordsFound);
						posts.add(post);
						continue;
					}

					String[] activityWordsFound = keywordsExtractor
							.extractActivityNames(message);
					String[] sportsEstablishmentsWordsFound = keywordsExtractor
							.extractSportsEstablishmentsNames(message);
					if (activityWordsFound.length > 0) {
						matchFound = true;
						post = new FBPost(p.getId(), p.getMessage(), timestamp,
								FBPostType.ACTIVITY, phrImage,
								activityWordsFound);
						posts.add(post);
						continue;
					} else if (sportsEstablishmentsWordsFound.length > 0) {
						matchFound = true;
						post = new FBPost(p.getId(), p.getMessage(), timestamp,
								FBPostType.SPORTS_ESTABLISHMENTS, phrImage,
								sportsEstablishmentsWordsFound);
						posts.add(post);
						continue;
					}

					if (matchFound == false) {
						post = new FBPost(p.getId(), message, timestamp,
								FBPostType.UNRELATED, phrImage, null);
						posts.add(post);
					}
				}
			}
		}
		return posts;
	}

	private List<FBPost> filterPhotosList(List<Photo> newPhotos)
			throws ImageHandlerException, DataAccessException {
		List<FBPost> posts = new ArrayList<>();
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
					Image imageFromPost = ImageHandler.getImageFromURL(p
							.getPicture());
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
						continue;
					} else if (restaurantsWordsFound.length > 0) {
						matchFound = true;
						post = new FBPost(p.getId(), message, timestamp,
								FBPostType.RESTAURANT, phrImage,
								restaurantsWordsFound);
						posts.add(post);
						continue;
					}

					String[] activityWordsFound = keywordsExtractor
							.extractActivityNames(message);
					String[] sportsEstablishmentsWordsFound = keywordsExtractor
							.extractSportsEstablishmentsNames(message);
					if (activityWordsFound.length > 0) {
						matchFound = true;
						post = new FBPost(p.getId(), p.getName(), timestamp,
								FBPostType.ACTIVITY, phrImage,
								activityWordsFound);
						posts.add(post);
						continue;
					} else if (sportsEstablishmentsWordsFound.length > 0) {
						matchFound = true;
						post = new FBPost(p.getId(), p.getName(), timestamp,
								FBPostType.SPORTS_ESTABLISHMENTS, phrImage,
								sportsEstablishmentsWordsFound);
						posts.add(post);
						continue;
					}

					if (matchFound == false) {
						post = new FBPost(p.getId(), message, timestamp,
								FBPostType.UNRELATED, phrImage, null);
						posts.add(post);
					}
				}
			}
		}

		return posts;
	}

	@Override
	public List<FBPost> getNewPosts(Timestamp timestamp,
			String userFBAccessToken, String userAccessToken)
			throws DataAccessException {
		List<Post> feed = getAllPostsFromFB(userFBAccessToken);
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

			List<Photo> photos = getAllPhotosFromFB(userFBAccessToken);
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
			filteredPosts.addAll(filteredPhotos);
			return filteredPosts;
		} catch (ImageHandlerException | ServiceException e) {
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
